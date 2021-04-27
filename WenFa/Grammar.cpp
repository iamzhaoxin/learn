#include "Grammar.h"
#include <stack>

Grammar::Grammar()
{
	int i, j;

#pragma region ��ʼ���սἯ����սἯ
	for (i = 97, j = 65; (i <= 122) && (j <= 90); i++, j++)
	{
		this->Vt.insert(char(i));
		this->Vn.insert(char(j));
	}
	// �ر�ģ��ս������$��ʾ�մ���#��ʾ������
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

// �������캯��
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

// �û������ʼ���ķ�
void Grammar::init()
{
#pragma region ��ʼ����ʼ���źͲ���ʽ����
	// �û����뿪ʼ����
	char start;
	while (true)
	{
		cout << "�����뿪ʼ���ţ�";
		cin >> start;
		if (inVn(start))
		{
			this->S = start;
			break;
		}
		else
		{
			cout << "��ʼ����Ӧ�ڷ��սἯ�У�����������" << endl;
		}
	}
	cout << endl;
#pragma endregion

	// �û��������ʽ����
	init_P();
}

// ��ʼ������ʽ����
void Grammar::init_P()
{
	int i, flag;
	this->pnum = 0;
	cout << "���������ʽ��(����0����)" << endl;
	cout << "����:S-aA���մ���$��ʾ" << endl << endl;
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
				cout << "����ʽ���ӦΪ���ս��������������" << endl << endl;
			}
			else
			{
				string right;
				int i = p.find("-");
				if (i == string::npos)
				{
					cout << "����ʽ��ʽ����!" << endl;
					flag = 1;
				}
				else
				{
					right = p.substr(i + 1, p.length());

					for (i = 0; i < right.length(); i++)
					{
						if (!inVt(right[i]) && !inVn(right[i]))
						{
							cout << "����ʽ�󲿺��Ƿ��ַ���";
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
							// �ظ���־
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
					cout << "����ʽ��ӳɹ�" << endl << endl;
				}
				else
				{
					cout << "�������µĲ���ʽ��" << endl << endl;
				}
			}
		}
	}
	this->terminal.insert('#');
}

// ������ʽp�Ƿ��ڲ���ʽ������
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
for(i��1��i<=n��i++)
   for(j��1��j<=i��1��j++)
   { ������Ai��Aj�õĲ���ʽ��д��Ai����1�� /��2�� /��/��k��
	   ����Aj����1 /��2 /��/��k�ǹ��ڵ�Ajȫ������
	   ����Ai�����е�ֱ����ݹ飻
   }
*/

// ������ʽ�Ҳ��һ���ַ�Ϊ���ս���Ľ��д����滻
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

// �����ɴ�Ĳ���ʽɾ��
void Grammar::del_unreachable_production()
{
	int i, j;
	// ��¼�ܹ��Ƶ����ķ��ս����
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
	// ��������ʽ���������˲���reachable�У���׼������ɾ��
	set<int> del_index;
	for (i = 0; i < this->pnum; i++)
	{
		if (reachable.find(this->P[i].left) == reachable.end())
			del_index.insert(i);
	}
	// �±��ɴ�С��ɾ�����ɴ����ʽ
	set<int>::reverse_iterator del_iter;
	for (del_iter = del_index.rbegin(); del_iter != del_index.rend(); del_iter++)
	{
		this->P.erase(this->P.begin() + *del_iter);
	}
	this->pnum = this->P.size();
}

// ��ȡ����ʽ
void Grammar::parsing_common_factor()
{
	int i, j;
	char new_nt = 90;	// �������ս������Z��ʼ��A����
	int flag = 1;		// ���������ʽ���Ǿ�Ҫ�Բ���ʽ���������ı䣬flag��1
	while (flag != 0)
	{
		flag = 0;
		set<int> lcf;	// ��һ��set��¼ӵ�������ӵĲ���ʽ�±꣬����ɾ���Ͳ������
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
							new_right = '$';	// S->ab����ȡab��Z->��
						}
						else
						{
							new_right = this->P[*lcf_iter].right.substr(length, this->P[*lcf_iter].right.length());  // S->abB����ȡab��Z->B
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

// ������ݹ�
void Grammar::parsing_direct_left_recursion()
{
	int i, j;
	char new_nt = 90;	// �������ս������Z��ʼ��A����
	int flag = 1;
	while (flag)
	{
		flag = 0;
		set<char>::iterator nt_it;
		for (nt_it = this->nonterminal.begin(); nt_it != this->nonterminal.end(); nt_it++)
		{
			set<int> recur_index;	// ��ݹ����ʽ���ϣ���S->Sa, S->Sb
			set<int> end_index;		// �ǵݹ�ʽ���ϣ���Ϊ�ս�
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

				// �Եݹ鲿�֣�����S'->aS'
				set<int>::iterator recur_iter;
				for (recur_iter = recur_index.begin(); recur_iter != recur_index.end(); recur_iter++)
				{
					pstring newp;
					newp.left = new_nt;
					newp.right = this->P[*recur_iter].right.substr(1, P[*recur_iter].right.length());
					newp.right += new_nt;
					this->P.push_back(newp);
				}

				// �Էǵݹ鲿�֣�����S->aS'
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

// ����First����
void Grammar::cal_First()
{
	// ���ȸ�ÿ�����ս����ʼ��һ���յ�First����
	set<char>::iterator nterm_iter;
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		set<char> value;
		this->first_set.insert(pair<char, set<char>>(*nterm_iter, value));
	}
	// ��ÿ�����ս����First����
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		get_First_recur(*nterm_iter);
	}
}

// ���õݹ鷽������Follow����
void Grammar::cal_Follow_recur()
{
	// ���ȸ�ÿ�����ս����ʼ��Follow���ϣ��ر�Ķ�S���#������
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

	// ��ÿ�����ս����Follow����
	for (nterm_iter = this->nonterminal.begin(); nterm_iter != this->nonterminal.end(); nterm_iter++)
	{
		get_Follow_recur(*nterm_iter, 1);
	}
}

// ��ʹ�õݹ����Follow����
void Grammar::cal_Follow()
{
	// ���ȸ�ÿ�����ս����ʼ��Follow���ϣ��ر�Ķ�S���#������
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

// ����Select����
void Grammar::cal_Select()
{
	int i, j;
	for (i = 0; i < this->pnum; i++)
	{
		int countEmpty = 0;			// �Ҷ�ȫ�����Ƴ�$����ʹ�� Select(A-a) = First(a)-$ �� Follow(A)
		set<char> select;
		for (j = 0; j < this->P[i].right.length(); j++)
		{
			char rj = this->P[i].right[j];
			// �����ս��
			if (inVt(rj))
			{
				if (rj != '$')	// ���ǿմ�����ֱ�Ӽ��벢����
					select.insert(this->P[i].right[j]);
				else if (rj == '$')
					countEmpty++;
				break;
			}
			// �������ս��
			else
			{
				// ����First�����з�$������
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

		// ��Follow���ϲ���
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

// ����Ԥ���������ͬһ��Ԫ���ж��ʽ��ʱ������false
bool Grammar::get_Table()
{
	int i, j;

#pragma region ��ʼ��Ԥ�������
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

// �ж��Ƿ�ΪLL(1)�ķ�
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
		cout << "������һ������:";
		cin >> sentence;
		for (i = 0; i < sentence.length(); i++)
		{
			if (inVn(sentence[i]))
			{
				cout << "syntax error! ���в�Ӧ���ַ��ս��" << endl;
				return false;
			}
			if (!(inVn(sentence[i]) || inVt(sentence[i])))
			{
				cout << "syntax error! ���к��Ƿ��ַ�" << endl;
				return false;
			}
		}
		if (sentence[i - 1] != '#')
			sentence += '#';

		// ��������ջ����#�Ϳ�ʼ������ջ
		stack<char> s;
		s.push('#');
		s.push(this->S);

		// �����ͷ
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
						cout << "syntax error! �޷�ƥ��" << endl;
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
					cout << "syntax error! �޷�ƥ��" << endl;
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
	cout << "syntax error! �޷�ƥ��" << endl;
	return false;
}

// ��target��First����
void Grammar::get_First_recur(char target)
{
	int i, j;
	int isEmpty = 0;		// �����մ���ʶ����0��ʾ���ܲ���$
	int countEmpty = 0;		// ֻ�е�X->Y1Y2...Yn�У�Y1-Yn�����Բ����մ�ʱ��First(X)����#
	for (i = 0; i < this->pnum; i++)
	{
		// �����ʽ���ƥ��
		if (this->P[i].left == target)
		{
			// �ս��ֱ�Ӽ���First
			if (inVt(this->P[i].right[0]))
			{
				this->first_set[target].insert(this->P[i].right[0]);
			}
			else
			{
				// X->Y1Y2...Yj...Yk�����ı��ʽ
				for (j = 0; j < this->P[i].right.length(); j++)
				{
					char Yj = this->P[i].right[j];
					// ���Yj���ս������ֹͣ�ݹ�
					if (inVt(Yj))
					{
						this->first_set[target].insert(Yj);
						break;
					}
					// Yj�Ƿ��ս����Ӧ�ݹ飬�����Yj��First��
					get_First_recur(Yj);

					// ��Yj�Ľ�����Ƹ�X
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

					if (isEmpty == 0)	// Yj���ܲ����գ���������
					{
						break;
					}
					else       // ����ܲ����մ�����ô��Ҫȷ���Ҳ�ȫ���ܲ����մ�
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

// �ݹ����target��Follow����
void Grammar::get_Follow_recur(char target, int recur_count)
{
	int i, j;
	for (i = 0; i < this->pnum; i++)
	{
		// �������ݹ��������ֹ�ҵݹ����ʽ��Followʱ��ѭ��
		if (recur_count > this->pnum)
		{
			break;
		}
		int index = this->P[i].right.find(target);	// �ҵ�target�ڲ���ʽ��P[i]�Ҷ˵��±�

		// !npos��ʾ�ҵ�target�����������S->aAB�����Ĳ���ʽ(A��Ϊ����)
		if (index != string::npos && index < this->P[i].right.length() - 1)
		{
			char next = this->P[i].right[index + 1];
			// ������ս��ֱ�Ӽ���
			if (inVt(next))
			{
				this->follow_set[target].insert(next);
			}
			else
			{
				int hasEmpty = 0;	// �����ս����ʶ
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

				// ��Ϊ S->aABC����First(B)����$ʱ��������Follow(A)+= First(B)-$�����������Ĳ��֣�Ȼ����Follow(A)+=Follow(B)
				if (hasEmpty == 1 && ((index + 1) < this->P[i].right.length() - 1))
				{
					get_Follow_recur(next, ++recur_count);
					set<char>::iterator next_iter;
					for (next_iter = this->follow_set[next].begin(); next_iter != this->follow_set[next].end(); next_iter++)
					{
						this->follow_set[target].insert(*next_iter);
					}
				}

				// ���� S->aAB����First(B)����$ʱ��Follow(A)+=Follow(S)����target����ķ��ս��Ϊ���Ҷ�
				// �ر�ģ����� S->aSA��First(A)����$����Ҫ�������޵ݹ�
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
		// �������� S->aA����Follow(A)+=Follow(S)
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

// �ǵݹ����target��Follow����
void Grammar::get_Follow()
{
	int i, j;
	int flag = 1;		// ������Follow���ϲ��ٱ仯ʱ��ֹͣѭ��

	while (flag)
	{
		flag = 0;		// ��flagΪ0�������޸�ʱ��Ϊ1

		// ��ÿ������ʽ
		for (i = 0; i < this->pnum; i++)
		{
			char left = this->P[i].left;
			for (j = 0; j < this->P[i].right.length(); j++)
			{
				char rj = this->P[i].right[j];
				if (inVn(rj))
				{
					char brj;	// behind rj
					int psize = this->follow_set[rj].size();	// previous size��Follow(rj)��ԭʼֵ
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

							if (emptyFlag == 1)		// A->aBCD��C�����Ƴ��ţ�Follow(B)+=Follow(C)
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

// �ж��ַ��Ƿ�Ϊ���ս��
bool Grammar::inVn(char s)
{
	if (this->Vn.find(s) != this->Vn.end())
		return true;
	return false;
}

// �жϲ���ʽ�Ƿ��������ʽ
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

// �жϲ���ʽ�Ƿ������ݹ�ʽ
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

// �ж��ַ��Ƿ�Ϊ�ս��
bool Grammar::inVt(char e)
{
	if (this->Vt.find(e) != this->Vt.end())
		return true;
	return false;
}

// ���ս����Ԥ��������е��б�
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

// ����ս����Ԥ��������е��б�
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

// �������ʽ��
void Grammar::printProduction()
{
	int i;
	cout << "--------------����ʽ����---------------" << endl << endl;
	for (i = 0; i < this->pnum; i++)
	{
		cout << this->P[i].left << "->" << this->P[i].right << endl;
	}
	cout << endl;
}

// ���First����
void Grammar::printFirst()
{
	cout << "-----------First����-------------" << endl << endl;
	map<char, set<char>>::iterator first_iter;
	for (first_iter = this->first_set.begin(); first_iter != this->first_set.end(); first_iter++)
	{
		cout << "���ս��" << first_iter->first << ":";
		set<char>::iterator value_iter;
		for (value_iter = first_iter->second.begin(); value_iter != first_iter->second.end(); value_iter++)
		{
			cout << *value_iter << " ";
		}
		cout << endl;
	}
	cout << endl;
}

// ���Follow����
void Grammar::printFollow()
{
	cout << "-----------Follow����-------------" << endl << endl;
	map<char, set<char>>::iterator follow_iter;
	for (follow_iter = this->follow_set.begin(); follow_iter != this->follow_set.end(); follow_iter++)
	{
		cout << "���ս��" << follow_iter->first << ":";
		set<char>::iterator value_iter;
		for (value_iter = follow_iter->second.begin(); value_iter != follow_iter->second.end(); value_iter++)
		{
			cout << *value_iter << " ";
		}
		cout << endl;
	}
	cout << endl;
}

// ���Select����
void Grammar::printSelect()
{
	cout << "------------Select����------------" << endl << endl;
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

// ���Ԥ�������
void Grammar::printTable()
{
	int i, j;
	cout << "-------------Ԥ�������---------------" << endl << endl;
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

