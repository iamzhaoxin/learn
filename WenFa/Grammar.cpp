#include "Grammar.h"
#include <stack>

Grammar::Grammar()
{
	int i, j;

#pragma region 初始化终结集与非终结集
	for (i = 97, j = 65; (i <= 122) && (j <= 90); i++, j++)
	{
		this->Vt.insert(char(i));
		this->Vn.insert(char(j));
	}
	// 特别的，终结符中用$表示空串，#表示结束符
	Vt.insert('$');
	Vt.insert('#');
#pragma endregion

	this->P.clear();
	this->terminal.clear();
	this->nonterminal.clear();
	this->first_set.clear();
	this->follow_set.clear();
	this->select_set.clear();
	this->predict_table = new int* [1];
	this->pnum = 0;
	this->S = 'S';
	this->LL1 = false;
}

// 拷贝构造函数
Grammar::Grammar(const Grammar& g)
{
	this->Vn = g.Vn;
	this->Vt = g.Vt;
	this->P = g.P;
	this->terminal = g.terminal;
	this->nonterminal = g.nonterminal;
	this->first_set = g.first_set;
	this->follow_set = g.follow_set;
	this->select_set = g.select_set;
	this->predict_table = new int* [1];
	this->pnum = g.pnum;
	this->S = g.S;
	this->LL1 = g.LL1;
}

// 用户输入初始化文法
void Grammar::init()
{
#pragma region 初始化开始符号和产生式集合
	// 用户输入开始符号
	char start;
	while (true)
	{
		cout << "请输入开始符号：";
		cin >> start;
		if (inVn(start))
		{
			this->S = start;
			break;
		}
		else
		{
			cout << "开始符号应在非终结集中！请重新输入" << endl;
		}
	}
	cout << endl;
#pragma endregion

	// 用户输入产生式集合
	init_P();
}

// 初始化产生式集合
void Grammar::init_P()
{
	int i, flag;
	this->pnum = 0;
	cout << "请输入产生式：(输入0结束)" << endl;
	cout << "形如:S-aA，空串以$表示" << endl << endl;
	while (true)
	{
		flag = 0;
		string p;
		cin >> p;
		if (p == "0")
		{
			break;
		}
		else
		{
			char left = p[0];
			this->nonterminal.insert(left);
			if (!inVn(left))
			{
				cout << "产生式左端应为非终结符！请重新输入" << endl << endl;
			}
			else
			{
				string right;
				int i = p.find("-");
				if (i == string::npos)
				{
					cout << "产生式格式有误!" << endl;
					flag = 1;
				}
				else
				{
					right = p.substr(i + 1, p.length());

					for (i = 0; i < right.length(); i++)
					{
						if (!inVt(right[i]) && !inVn(right[i]))
						{
							cout << "产生式左部含非法字符！";
							flag = 1;
							break;
						}
						if (inVt(right[i]))
						{
							this->terminal.insert(right[i]);
						}
						if (inVn(right[i]))
						{
							this->nonterminal.insert(right[i]);
						}
					}

					for (i = 0; i < this->P.size(); i++)
					{
						if (this->P[i].left == left && this->P[i].right == right)
						{
							// 重复标志
							flag = 1;
							break;
						}
					}
				}

				if (flag != 1)
				{
					pstring temp;
					temp.left = left;
					temp.right = right;
					this->P.push_back(temp);
					this->pnum++;
					cout << "产生式添加成功" << endl << endl;
				}
				else
				{
					cout << "请输入新的产生式：" << endl << endl;
				}
			}
		}
	}
	this->terminal.insert('#');
}

// 检查产生式p是否在产生式集合中
bool find_production(vector<pstring> Pset, pstring p)
{
	int i;
	for (i = 0; i < Pset.size(); i++)
	{
		if (Pset[i].left == p.left && Pset[i].right == p.right)
			return true;
	}
	return false;
}

/*
for(i＝1；i<=n；i++)
   for(j＝1；j<=i－1；j++)
   { 把形如Ai→Ajγ的产生式改写成Ai→δ1γ /δ2γ /…/δkγ
	   其中Aj→δ1 /δ2 /…/δk是关于的Aj全部规则；
	   消除Ai规则中的直接左递归；
   }
*/

// 将产生式右侧第一个字符为非终结符的进行代入替换
void Grammar::first_letter_substitution()
{
	int i, j, m, n;
	set<char>::iterator it1, it2;
	for (it1 = this->nonterminal.begin(), i = 0; it1 != this->nonterminal.end(); it1++, i++)
	{
		for (it2 = this->nonterminal.begin(), j = 0; j < i; it2++, j++)
		{
			vector<pstring> newP;
			for (m = 0; m < this->pnum; m++)
			{
				if (this->P[m].left == *it1 && this->P[m].right[0] == *it2)
				{
					for (n = 0; n < this->pnum; n++)
					{
						if (this->P[n].left == *it2)
						{
							pstring newp;
							newp.left = *it1;
							if (this->P[n].right != "$")
								newp.right = this->P[n].right + this->P[m].right.substr(1);
							else
								newp.right = this->P[m].right.substr(1);
							newP.push_back(newp);
						}
					}
				}
			}

			set<int> erase_index;
			for (m = 0; m < this->pnum; m++)
			{
				if (this->P[m].left == *it1)
				{
					erase_index.insert(m);
					if (this->P[m].right[0] != *it2)
					{
						newP.push_back(this->P[m]);
					}
				}
			}

			set<int>::reverse_iterator erase_it;
			for (erase_it = erase_index.rbegin(); erase_it != erase_index.rend(); erase_it++)
			{
				this->P.erase(this->P.begin() + *erase_it);
			}

			for (auto p_it = newP.begin(); p_it != newP.end(); p_it++)
			{
				this->P.push_back(*p_it);
			}
			this->pnum = this->P.size();
		}
	}

}

// 将不可达的产生式删除
void Grammar::del_unreachable_production()
{
	int i, j;
	// 记录能够推导到的非终结符集
	set<char> reachable;
	stack<char> s;
	s.push(this->S);
	while (!s.empty())
	{
		char ch = s.top();
		s.pop();
		reachable.insert(ch);
		for (i = 0; i < this->pnum; i++)
		{
			if (this->P[i].left == ch)
			{
				for (j = 0; j < this->P[i].right.length(); j++)
				{
					if (inVn(this->P[i].right[j]))
					{
						if (reachable.find(this->P[i].right[j]) == reachable.end())
						{
							reachable.insert(this->P[i].right[j]);
							s.push(this->P[i].right[j]);
						}

					}
				}
			}
		}
	}

	this->nonterminal = reachable;
	// 遍历产生式集，如果左端不在reachable中，则准备将其删除
	set<int> del_index;
	for (i = 0; i < this->pnum; i++)
	{
		if (reachable.find(this->P[i].left) == reachable.end())
			del_index.insert(i);
	}
	// 下标由大到小的删除不可达产生式
	set<int>::reverse_iterator del_iter;
	for (del_iter = del_index.rbegin(); del_iter != del_index.rend(); del_iter++)
	{
		this->P.erase(this->P.begin() + *del_iter);
	}
	this->pnum = this->P.size();
}

// 提取左公因式
void Grammar::parsing_common_factor()
{
	int i, j;
	char new_nt = 90;	// 新增非终结符，从Z开始向A加入
	int flag = 1;		// 如果有左公因式，那就要对产生式集合做出改变，flag置1
	while (flag != 0)
	{
		flag = 0;
		set<int> lcf;	// 用一个set记录拥有左公因子的产生式下标，倒序删除就不会打乱
		for (i = 0; i < this->pnum; i++)
		{
			lcf.clear();
			char left = this->P[i].left;
			string right;
			int length;
			for (length = this->P[i].right.length(); length >= 1; length--)
			{
				lcf.clear();
				lcf.insert(i);
				right = this->P[i].right.substr(0, length);
				for (j = 0; j < this->pnum; j++)
				{
					if (j != i)
					{
						if (this->P[j].left == left)
						{
							if (this->P[j].right.length() >= length)
							{
								if (this->P[j].right.substr(0, length) == right)
								{
									lcf.insert(j);
								}
							}
						}
					}
				}
				if (lcf.size() > 1)
				{
					flag = 1;
					while (this->nonterminal.find(new_nt) != this->nonterminal.end())
						new_nt--;
					this->nonterminal.insert(new_nt);

					set<int>::reverse_iterator lcf_iter;
					for (lcf_iter = lcf.rbegin(); lcf_iter != lcf.rend(); lcf_iter++)
					{
						string new_right;
						if (this->P[*lcf_iter].right.length() == length)
						{
							new_right = '$';	// S->ab，提取ab后，Z->ε
						}
						else
						{
							new_right = this->P[*lcf_iter].right.substr(length, this->P[*lcf_iter].right.length());  // S->abB，提取ab后，Z->B
						}
						pstring new_p;
						new_p.left = new_nt;
						new_p.right = new_right;
						this->P.erase(this->P.begin() + *lcf_iter);
						this->P.push_back(new_p);
					}

					pstring new_p2;
					new_p2.left = left;
					new_p2.right += right;
					new_p2.right += new_nt--;
					this->P.push_back(new_p2);
				}

				if (flag == 1)
				{
					this->pnum = this->P.size();
					break;
				}
			}

			if (flag == 1)
			{
				break;
			}
		}
	}
}

// 消除左递归
void Grammar::parsing_direct_left_recursion()
{
	int i, j;
	char new_nt = 90;	// 新增非终结符，从Z开始向A加入
	int flag = 1;
	while (flag)
	{
		flag = 0;
		set<char>::iterator nt_it;
		for (nt_it = this->nonterminal.begin(); nt_it != this->nonterminal.end(); nt_it++)
		{
			set<int> recur_index;	// 左递归产生式集合，如S->Sa, S->Sb
			set<int> end_index;		// 非递归式集合，作为终结
			for (i = 0; i < this->pnum; i++)
			{
				if (this->P[i].left == *nt_it)
				{
					if (this->P[i].left == this->P[i].right[0])
					{
						recur_index.insert(i);
					}
					else
					{
						end_index.insert(i);
					}
				}
			}

			if (recur_index.size() != 0)
			{
				flag = 1;
				while (this->nonterminal.find(new_nt) != this->nonterminal.end())
					new_nt--;
				this->nonterminal.insert(new_nt);

				// 对递归部分，构造S'->aS'
				set<int>::iterator recur_iter;
				for (recur_iter = recur_index.begin(); recur_iter != recur_index.end(); recur_iter++)
				{
					pstring newp;
					newp.left = new_nt;
					newp.right = this->P[*recur_iter].right.substr(1, P[*recur_iter].right.length());
					newp.right += new_nt;
					this->P.push_back(newp);
				}

				// 对非递归部分，构造S->aS'
				set<int>::iterator end_iter;
				for (end_iter = end_index.begin(); end_iter != end_index.end(); end_iter++)
				{
					pstring newp;
					newp.left = *nt_it;
					if (this->P[*end_iter].right != "$")
						newp.right = this->P[*end_iter].right;
					newp.right += new_nt;
					this->P.push_back(newp);
				}

				pstring epsilon;
				epsilon.left = new_nt;
				epsilon.right = "$";
				this->P.push_back(epsilon);

				set<int> erase_index(recur_index.begin(), recur_index.end());
				erase_index.insert(end_index.begin(), end_index.end());
				set<int>::reverse_iterator erase_it;
				for (erase_it = erase_index.rbegin(); erase_it != erase_index.rend(); erase_it++)
				{
					this->P.erase(this->P.begin() + *erase_it);
				}
			}

			if (flag == 1)
			{
				this->pnum = this->P.size();
				break;
			}
		}
	}
}

// 计算First集合
void Grammar::cal_First()
{
	// 首先给每个非终结符初始化一个空的First集合
	set<char>::iterator nterm_iter;
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		set<char> value;
		this->first_set.insert(pair<char, set<char>>(*nterm_iter, value));
	}
	// 对每个非终结符求First集合
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		get_First_recur(*nterm_iter);
	}
}

// 调用递归方法计算Follow集合
void Grammar::cal_Follow_recur()
{
	// 首先给每个非终结符初始化Follow集合，特别的对S添加#结束符
	set<char>::iterator nterm_iter;
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		set<char> value;
		if (*nterm_iter == this->S)
		{
			value.insert('#');
		}
		this->follow_set.insert(pair<char, set<char>>(*nterm_iter, value));
	}

	// 对每个非终结符求Follow集合
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		get_Follow_recur(*nterm_iter, 1);
	}
}

// 不使用递归计算Follow集合
void Grammar::cal_Follow()
{
	// 首先给每个非终结符初始化Follow集合，特别的对S添加#结束符
	set<char>::iterator nterm_iter;
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		set<char> value;
		if (*nterm_iter == this->S)
		{
			value.insert('#');
		}
		this->follow_set.insert(pair<char, set<char>>(*nterm_iter, value));
	}

	get_Follow();
}

// 计算Select集合
void Grammar::cal_Select()
{
	int i, j;
	for (i = 0; i < this->pnum; i++)
	{
		int countEmpty = 0;			// 右端全部能推出$，则使用 Select(A-a) = First(a)-$ ∪ Follow(A)
		set<char> select;
		for (j = 0; j < this->P[i].right.length(); j++)
		{
			char rj = this->P[i].right[j];
			// 遇到终结符
			if (inVt(rj))
			{
				if (rj != '$')	// 不是空串，则直接加入并结束
					select.insert(this->P[i].right[j]);
				else if (rj == '$')
					countEmpty++;
				break;
			}
			// 遇到非终结符
			else
			{
				// 将其First集合中非$符加入
				set<char>::iterator value_iter;
				for (value_iter = this->first_set[rj].begin(); value_iter != this->first_set[rj].end(); value_iter++)
				{
					if (*value_iter != '$')
						select.insert(*value_iter);
				}
				if (this->first_set[rj].find('$') != this->first_set[rj].end())
				{
					countEmpty++;
				}
				else
				{
					break;
				}
			}
		}

		// 将Follow集合并入
		if (countEmpty == this->P[i].right.length())
		{
			set<char>::iterator value_iter;
			for (value_iter = this->follow_set[this->P[i].left].begin(); value_iter != this->follow_set[this->P[i].left].end(); value_iter++)
			{
				select.insert(*value_iter);
			}
		}

		this->select_set.push_back(select);
	}
}

// 生成预测分析表，当同一单元格有多个式子时，返回false
bool Grammar::get_Table()
{
	int i, j;

#pragma region 初始化预测分析表
	this->predict_table = new int* [this->nonterminal.size()];
	for (i = 0; i < this->nonterminal.size(); i++)
	{
		this->predict_table[i] = new int[this->terminal.size()];
	}
	for (i = 0; i < this->nonterminal.size(); i++)
	{
		for (j = 0; j < this->terminal.size(); j++)
		{
			this->predict_table[i][j] = -1;
		}
	}
#pragma endregion

	for (i = 0; i < this->pnum; i++)
	{
		int row = index_in_nonterminal(this->P[i].left);
		set<char>::iterator s_iter;
		for (s_iter = this->select_set[i].begin(); s_iter != this->select_set[i].end(); s_iter++)
		{
			int column = index_in_terminal(*s_iter);
			if (this->predict_table[row][column] != -1)
			{
				return false;
			}
			else
			{
				this->predict_table[row][column] = i;
			}
		}
	}
	return true;

}

// 判断是否为LL(1)文法
bool Grammar::is_LL1()
{
	if (!get_Table())
	{
		this->LL1 = false;
		return false;
	}
	this->LL1 = true;
	return true;
}

bool Grammar::grammar_parsing()
{
	int i, j = 0;
	if (this->LL1)
	{
		string sentence;
		cout << "请输入一个句子:";
		cin >> sentence;
		for (i = 0; i < sentence.length(); i++)
		{
			if (inVn(sentence[i]))
			{
				cout << "syntax error! 句中不应出现非终结符" << endl;
				return false;
			}
			if (!(inVn(sentence[i]) || inVt(sentence[i])))
			{
				cout << "syntax error! 句中含非法字符" << endl;
				return false;
			}
		}
		if (sentence[i - 1] != '#')
			sentence += '#';

		// 构造运算栈，将#和开始符号入栈
		stack<char> s;
		s.push('#');
		s.push(this->S);

		// 定义读头
		char readhead = sentence[j++];
		while (s.size() != 1)
		{
			char top = s.top();
			s.pop();

			if (inVt(top))
			{
				if (top != '$')
				{
					if (top == readhead)
						readhead = sentence[j++];
					else
					{
						cout << "syntax error! 无法匹配" << endl;
						return false;
					}
				}
			}
			else
			{
				int row = index_in_nonterminal(top);
				int column = index_in_terminal(readhead);
				int index = this->predict_table[row][column];
				if (index == -1)
				{
					cout << "syntax error! 无法匹配" << endl;
					return false;
				}
				else
				{
					int k;
					for (k = this->P[index].right.length() - 1; k >= 0; k--)
					{
						s.push(this->P[index].right[k]);
					}
				}
			}
		}

		if (s.top() == readhead)
			return true;
	}
	cout << "syntax error! 无法匹配" << endl;
	return false;
}

// 求target的First集合
void Grammar::get_First_recur(char target)
{
	int i, j;
	int isEmpty = 0;		// 产生空串标识符，0表示不能产生$
	int countEmpty = 0;		// 只有当X->Y1Y2...Yn中，Y1-Yn都可以产生空串时，First(X)才有#
	for (i = 0; i < this->pnum; i++)
	{
		// 与产生式左端匹配
		if (this->P[i].left == target)
		{
			// 终结符直接加入First
			if (inVt(this->P[i].right[0]))
			{
				this->first_set[target].insert(this->P[i].right[0]);
			}
			else
			{
				// X->Y1Y2...Yj...Yk这样的表达式
				for (j = 0; j < this->P[i].right.length(); j++)
				{
					char Yj = this->P[i].right[j];
					// 如果Yj是终结符，则停止递归
					if (inVt(Yj))
					{
						this->first_set[target].insert(Yj);
						break;
					}
					// Yj是非终结符则应递归，先求出Yj的First集
					get_First_recur(Yj);

					// 将Yj的结果复制给X
					set<char>::iterator value_iter;
					for (value_iter = this->first_set[Yj].begin(); value_iter != this->first_set[Yj].end(); value_iter++)
					{
						if (*value_iter == '$')
						{
							isEmpty = 1;
						}
						else
						{
							this->first_set[target].insert(*value_iter);
						}
					}

					if (isEmpty == 0)	// Yj不能产生空，迭代结束
					{
						break;
					}
					else       // 如果能产生空串，那么需要确认右侧全都能产生空串
					{
						countEmpty += 1;
						isEmpty = 0;
					}
				}
				if (countEmpty == this->P[i].right.length())
				{
					this->first_set[target].insert('$');
				}
			}
		}
	}
}

// 递归的求target的Follow集合
void Grammar::get_Follow_recur(char target, int recur_count)
{
	int i, j;
	for (i = 0; i < this->pnum; i++)
	{
		// 限制最大递归次数，防止右递归产生式求Follow时死循环
		if (recur_count > this->pnum)
		{
			break;
		}
		int index = this->P[i].right.find(target);	// 找到target在产生式中P[i]右端的下标

		// !npos表示找到target，下面对形如S->aAB这样的产生式(A不为最右)
		if (index != string::npos && index < this->P[i].right.length() - 1)
		{
			char next = this->P[i].right[index + 1];
			// 如果是终结符直接加入
			if (inVt(next))
			{
				this->follow_set[target].insert(next);
			}
			else
			{
				int hasEmpty = 0;	// 含有终结符标识
				set<char>::iterator next_iter;
				for (next_iter = this->first_set[next].begin(); next_iter != this->first_set[next].end(); next_iter++)
				{
					if (*next_iter == '$')
						hasEmpty = 1;
					else
					{
						this->follow_set[target].insert(*next_iter);
					}
				}

				// 若为 S->aABC，当First(B)含有$时，首先做Follow(A)+= First(B)-$，即上面做的部分；然后做Follow(A)+=Follow(B)
				if (hasEmpty == 1 && ((index + 1) < this->P[i].right.length() - 1))
				{
					get_Follow_recur(next, ++recur_count);
					set<char>::iterator next_iter;
					for (next_iter = this->follow_set[next].begin(); next_iter != this->follow_set[next].end(); next_iter++)
					{
						this->follow_set[target].insert(*next_iter);
					}
				}

				// 仅对 S->aAB，当First(B)含有$时，Follow(A)+=Follow(S)。即target后面的非终结符为最右端
				// 特别的，对于 S->aSA，First(A)含有$，需要避免无限递归
				if (hasEmpty == 1 && ((index + 1) == this->P[i].right.length() - 1) && this->P[i].left != target)
				{
					get_Follow_recur(this->P[i].left, ++recur_count);
					set<char>::iterator left_iter;
					for (left_iter = this->follow_set[this->P[i].left].begin(); left_iter != this->follow_set[this->P[i].left].end(); left_iter++)
					{
						this->follow_set[target].insert(*left_iter);
					}
				}
			}
		}
		// 对于形如 S->aA，则Follow(A)+=Follow(S)
		else if (index != string::npos && index == this->P[i].right.length() - 1 && target != this->P[i].left)
		{
			get_Follow_recur(this->P[i].left, ++recur_count);
			set<char>::iterator left_iter;
			for (left_iter = this->follow_set[this->P[i].left].begin(); left_iter != this->follow_set[this->P[i].left].end(); left_iter++)
			{
				this->follow_set[target].insert(*left_iter);
			}
		}
	}
}

// 非递归的求target的Follow集合
void Grammar::get_Follow()
{
	int i, j;
	int flag = 1;		// 当所有Follow集合不再变化时，停止循环

	while (flag)
	{
		flag = 0;		// 设flag为0，当有修改时设为1

		// 对每个产生式
		for (i = 0; i < this->pnum; i++)
		{
			char left = this->P[i].left;
			for (j = 0; j < this->P[i].right.length(); j++)
			{
				char rj = this->P[i].right[j];
				if (inVn(rj))
				{
					char brj;	// behind rj
					int psize = this->follow_set[rj].size();	// previous size，Follow(rj)的原始值
					if (j < this->P[i].right.length() - 1)	// Follow(rj)+=First(brj)
					{
						brj = this->P[i].right[j + 1];
						if (inVt(brj))
						{
							this->follow_set[rj].insert(brj);
						}
						else
						{
							int emptyFlag = 0;
							set<char>::iterator brj_iter;
							for (brj_iter = this->first_set[brj].begin(); brj_iter != this->first_set[brj].end(); brj_iter++)
							{
								if (*brj_iter != '$')
									this->follow_set[rj].insert(*brj_iter);
								else
									emptyFlag = 1;
							}

							if (emptyFlag == 1)		// A->aBCD，C若能推出ε，Follow(B)+=Follow(C)
							{
								for (brj_iter = this->follow_set[brj].begin(); brj_iter != this->follow_set[brj].end(); brj_iter++)
								{
									this->follow_set[rj].insert(*brj_iter);
								}
							}
						}
					}
					else    // Follow(rj)+=Follow(left)
					{
						set<char>::iterator left_iter;
						for (left_iter = this->follow_set[left].begin(); left_iter != this->follow_set[left].end(); left_iter++)
						{
							this->follow_set[rj].insert(*left_iter);
						}
					}
					if (psize != this->follow_set[rj].size())
						flag = 1;
				}
			}
		}
	}
}

// 判断字符是否为非终结符
bool Grammar::inVn(char s)
{
	if (this->Vn.find(s) != this->Vn.end())
		return true;
	return false;
}

// 判断产生式是否存在左公因式
bool Grammar::have_common_factor()
{
	int i, j;
	Grammar temp(*this);
	temp.first_letter_substitution();

	set<char>::iterator nt_iter;
	for (nt_iter = temp.nonterminal.begin(); nt_iter != temp.nonterminal.end(); nt_iter++)
	{
		for (i = 0; i < temp.pnum; i++)
		{
			if (temp.P[i].left == *nt_iter)
			{
				for (j = 0; j < temp.pnum; j++)
				{
					if (i != j && temp.P[j].left == *nt_iter)
					{
						if (temp.P[i].right[0] == temp.P[j].right[0])
							return true;
					}
				}
			}
		}
	}
	return false;
}

// 判断产生式是否存在左递归式
bool Grammar::have_left_recursion()
{
	int i;
	Grammar temp(*this);
	temp.first_letter_substitution();

	for (i = 0; i < temp.pnum; i++)
	{
		if (temp.P[i].left == temp.P[i].right[0])
		{
			return true;
		}
	}

	return false;
}

// 判断字符是否为终结符
bool Grammar::inVt(char e)
{
	if (this->Vt.find(e) != this->Vt.end())
		return true;
	return false;
}

// 求终结符在预测分析表中的列标
int Grammar::index_in_terminal(char target)
{
	int i;
	set<char>::iterator t_iter;
	for (t_iter = this->terminal.begin(), i = 0; t_iter != this->terminal.end(); t_iter++, i++)
	{
		if (*t_iter == target)
			return i;
	}
	return 0;
}

// 求非终结符在预测分析表中的行标
int Grammar::index_in_nonterminal(char target)
{
	int i;
	set<char>::iterator nt_iter;
	for (nt_iter = this->nonterminal.begin(), i = 0; nt_iter != this->nonterminal.end(); nt_iter++, i++)
	{
		if (*nt_iter == target)
			return i;
	}
	return 0;
}

// 输出产生式集
void Grammar::printProduction()
{
	int i;
	cout << "--------------产生式集合---------------" << endl << endl;
	for (i = 0; i < this->pnum; i++)
	{
		cout << this->P[i].left << "->" << this->P[i].right << endl;
	}
	cout << endl;
}

// 输出First集合
void Grammar::printFirst()
{
	cout << "-----------First集合-------------" << endl << endl;
	map<char, set<char>>::iterator first_iter;
	for (first_iter = this->first_set.begin(); first_iter != this->first_set.end(); first_iter++)
	{
		cout << "非终结符" << first_iter->first << ":";
		set<char>::iterator value_iter;
		for (value_iter = first_iter->second.begin(); value_iter != first_iter->second.end(); value_iter++)
		{
			cout << *value_iter << " ";
		}
		cout << endl;
	}
	cout << endl;
}

// 输出Follow集合
void Grammar::printFollow()
{
	cout << "-----------Follow集合-------------" << endl << endl;
	map<char, set<char>>::iterator follow_iter;
	for (follow_iter = this->follow_set.begin(); follow_iter != this->follow_set.end(); follow_iter++)
	{
		cout << "非终结符" << follow_iter->first << ":";
		set<char>::iterator value_iter;
		for (value_iter = follow_iter->second.begin(); value_iter != follow_iter->second.end(); value_iter++)
		{
			cout << *value_iter << " ";
		}
		cout << endl;
	}
	cout << endl;
}

// 输出Select集合
void Grammar::printSelect()
{
	cout << "------------Select集合------------" << endl << endl;
	int i;
	for (i = 0; i < this->pnum; i++)
	{
		cout << this->P[i].left << "->" << this->P[i].right << ":\t";
		set<char>::iterator iter;
		for (iter = this->select_set[i].begin(); iter != this->select_set[i].end(); iter++)
		{
			cout << *iter << " ";
		}
		cout << endl;
	}
	cout << endl;
}

// 输出预测分析表
void Grammar::printTable()
{
	int i, j;
	cout << "-------------预测分析表---------------" << endl << endl;
	set<char>::iterator it;
	for (it = this->terminal.begin(); it != this->terminal.end(); it++)
	{
		if (*it == '$')
			cout << "\t" << '#';
		else
			cout << "\t" << *it;
	}
	cout << endl;
	for (it = this->nonterminal.begin(), i = 0; it != this->nonterminal.end(); it++, i++)
	{
		cout << *it;
		for (j = 0; j < this->terminal.size(); j++)
		{
			if (this->predict_table[i][j] != -1)
			{
				cout << "\t";
				cout << this->P[this->predict_table[i][j]].left << "->" << this->P[this->predict_table[i][j]].right;
			}
			else
			{
				cout << "\t";
			}
		}
		cout << endl;
	}
	cout << endl;
}

Grammar::~Grammar()
{
	this->Vn.clear();
	this->Vt.clear();
	this->P.clear();
	this->terminal.clear();
	this->nonterminal.clear();
	this->first_set.clear();
	this->follow_set.clear();
	this->select_set.clear();
	delete[] this->predict_table;
}

