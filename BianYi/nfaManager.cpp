#include "nfamanager.h"
#include "graph.h"
#include <map>
#include<fstream>
#include<queue>
#include<vector>


nfaManager::nfaManager()
{
	int NumOfChar = 0;
}

//������ʽ�������ӷ���.��
string nfaManager::insert_concat(string regexp) {
	string ret = "";
	char c, c2;
	for (unsigned int i = 0; i < regexp.size(); i++) {
		c = regexp[i];
		if (i + 1 < regexp.size()) {
			c2 = regexp[i + 1];
			ret += c;
			if (c != '(' && c2 != ')' && c != '|' && c2 != '|' && c2 != '*') {
				ret += '.';
			}
		}
	}
	ret += regexp[regexp.size() - 1];//����ԭ������ʽ���һ���ַ�
	return ret;
}

int nfaManager::priority(char c) {
	if (c == '*')
		return 3;
	else if (c == '.')
		return 2;
	else if (c == '|')
		return 1;
	else return 0;
}

string nfaManager::regexp_to_postfix(string regexp)
{
	string postfix = "";
	stack<char> op;
	char c;
	for (unsigned int i = 0; i < regexp.size(); i++)
	{
		if ((regexp[i] >= 48 && regexp[i] <= 57) || (regexp[i] >= 65 && regexp[i] <= 90)
			|| (regexp[i] >= 97 && regexp[i] <= 122))
			postfix += regexp[i];
		else
			switch (regexp[i])
			{
			case '(':
				op.push(regexp[i]); break;
			case ')':
				while (op.top() != '(') {
					postfix += op.top();
					op.pop();
				}
				op.pop();
				break;
			default:
				while (!op.empty()) {
					c = op.top();
					if (priority(c) >= priority(regexp[i])) {
						postfix += op.top();
						op.pop();
					}
					else break;
				}
				op.push(regexp[i]);
			}
	}
	while (!op.empty())
	{
		postfix += op.top();
		op.pop();
	}
	return postfix;
}

void nfaManager::character(char c)
{
	st.push(nfa.numVertices);
	nfa.insertVertex();
	st.push(nfa.numVertices);
	nfa.insertVertex();
	nfa.insertEdge(nfa.numVertices - 2, nfa.numVertices - 1, c);
}

void nfaManager::union_()//����ѡ��
{
	nfa.insertVertex();
	nfa.insertVertex();
	int d = st.top(); st.pop();
	int c = st.top(); st.pop();
	int b = st.top(); st.pop();
	int a = st.top(); st.pop();
	nfa.insertEdge(nfa.numVertices - 2, a, 'e');
	nfa.insertEdge(nfa.numVertices - 2, c, 'e');
	nfa.insertEdge(b, nfa.numVertices - 1, 'e');
	nfa.insertEdge(d, nfa.numVertices - 1, 'e');
	st.push(nfa.numVertices - 2);
	st.push(nfa.numVertices - 1);

}

void nfaManager::concatenation()//��������
{
	int d = st.top(); st.pop();
	int c = st.top(); st.pop();
	int b = st.top(); st.pop();
	int a = st.top(); st.pop();
	nfa.insertEdge(b, c, 'e');
	st.push(a);
	st.push(d);
}

void nfaManager::kleene_star()//�հ�
{
	nfa.insertVertex();
	nfa.insertVertex();
	int b = st.top();
	st.pop();
	int a = st.top();
	st.pop();
	nfa.insertEdge(nfa.numVertices - 2, nfa.numVertices - 1, 'e');
	nfa.insertEdge(b, nfa.numVertices - 1, 'e');
	nfa.insertEdge(nfa.numVertices - 2, a, 'e');
	nfa.insertEdge(b, a, 'e');
	st.push(nfa.numVertices - 2);
	st.push(nfa.numVertices - 1);
}

void nfaManager::postfix_to_nfa(string postfix)
{
	for (unsigned int i = 0; i < postfix.size(); i++)
	{

		if ((postfix[i] >= 48 && postfix[i] <= 57) || (postfix[i] >= 65 && postfix[i] <= 90)
			|| (postfix[i] >= 97 && postfix[i] <= 122))
		{
			character(postfix[i]);
			NumOfChar++;
			chars.push_back(postfix[i]);
		}
		else
			switch (postfix[i])
			{
			case '*': kleene_star(); break;
			case '.': concatenation(); break;
			case '|': union_();
			}
	}

	int w = st.top();
	nfa.NodeTable[w].final = 1;//��ʶ���һ�����

	int m = st.top();
	st.pop();
	int n = st.top();
	st.pop();
	st.push(n);
	st.push(m);
	start_state = n;//��¼��ʼ����λ��

}

void nfaManager::show_nfa()
{
	ofstream out("output_nfa.txt");
	out << "digraph abc{" << endl;

	for (int i = 0; i < nfa.numVertices; i++)
	{
		//����ʾ��������
		out << nfa.NodeTable[i].data << "[fontcolor=white][shape=circle];" << endl;
	}

	//��ʼ�����ʾ��begin�������﷢�ֻ�ͼ�������ĻḲ��ǰ���
	out << nfa.NodeTable[start_state].data << "[fontcolor=black][label=begin];" << endl;

	for (int i = 0; i < nfa.numVertices; i++)
	{
		//���ܽ����ʾ��acc��
		if (nfa.NodeTable[i].final == 1)
			out << nfa.NodeTable[i].data << "[fontcolor=black][label=acc];" << endl;

		Edge* p = nfa.NodeTable[i].adj;
		while (p != NULL)
		{
			out << nfa.NodeTable[i].data << "->" << p->nextVertex << "[label=" << p->dest << "];" << endl;
			p = p->link;
		}
	}

	out << "}";
	out.close();
}

void nfaManager::getNeighbor(int v, char c, set<int>& di)
{
	Edge* p = nfa.NodeTable[v].adj;
	while (p != NULL)
	{
		if (p->dest == c)
			di.insert(p->nextVertex);
		p = p->link;
	}
}

void nfaManager::epsilon_closure(int v, set<int>& si)
{
	Edge* p = nfa.NodeTable[v].adj;
	while (p != NULL)
	{
		if (p->dest == 'e')
		{
			si.insert(p->nextVertex);
			epsilon_closure(p->nextVertex, si);
		}
		p = p->link;
	}
}

void nfaManager::nfa_to_dfa(set<int>& si)
{
	map<set<int>, int> mp;
	mp[si] = -1;
	queue<set<int> > que;

	si.clear();
	si.insert(start_state);
	int ct = 0;

	queue<int> s;

	epsilon_closure(start_state, si);
	if (mp.count(si) == 0) {
		mp[si] = ct++;
		que.push(si);
		dfa.insertVertex();
		s.push(dfa.numVertices - 1);
	}

	for (set<int>::iterator it = si.begin(); it != si.end(); ++it)
	{
		if (nfa.NodeTable[*it].final == 1)
			dfa.NodeTable[0].final = 1;
	}

	int h;
	while (que.size() != 0)
	{
		h = s.front();
		s.pop();

		si.empty();
		si = que.front();
		que.pop();

		for (int j = 0; j < chars.size(); j++)
		{
			set<int> di;
			//����һ��
			for (set<int>::iterator it = si.begin(); it != si.end(); ++it)
				getNeighbor(*it, chars[j], di);


			set<int> gi;//���ݣ�si -"��ĸ"-> di -"e"-> gi
			//����һ��
			for (set<int>::iterator ite = di.begin(); ite != di.end(); ++ite)
			{
				epsilon_closure(*ite, gi);
				gi.insert(*ite);
			}

			if (mp.count(gi) == 0)
			{
				mp[gi] = ct++;
				que.push(gi);
				dfa.insertVertex();
				s.push(dfa.numVertices - 1);
				dfa.insertEdge(h, ct - 1, chars[j]);
				int f1 = 0;
				for (set<int>::iterator it = gi.begin(); it != gi.end(); ++it)
				{
					if (nfa.NodeTable[*it].final == 1)
						f1 = 1;
				}
				dfa.NodeTable[dfa.numVertices - 1].final = f1;
			}
			else
			{
				dfa.insertEdge(h, mp[gi], chars[j]);
			}
			di.empty();
			gi.empty();

		}
	}
}

void nfaManager::show_dfa()
{
	ofstream out("output_dfa.txt");
	out << "digraph abc{"<<endl;

	for (int i = 0; i < dfa.numVertices; i++)
	{
		//����ʾ��������
		out << dfa.NodeTable[i].data << "[fontcolor=white][shape=circle];" << endl;//
	}

	//��ʼ�����ʾ��begin�������﷢�ֻ�ͼ�������ĻḲ��ǰ���
	out << dfa.NodeTable[0].data << "[fontcolor=black][label=begin];" << endl;

	for (int i = 0; i < dfa.numVertices; i++)
	{
		//���ܽ����ʾ��acc��
		if (dfa.NodeTable[i].final == 1)
			out << dfa.NodeTable[i].data << "[fontcolor=black][label=acc];" << endl;

		Edge* p = dfa.NodeTable[i].adj;
		while (p != NULL)
		{
			out << dfa.NodeTable[i].data << "->" << p->nextVertex << "[label=" << p->dest << "];" << endl;
			p = p->link;
		}
	}
	out << "}";
	out.close();
}

int nfaManager::dfa_transform(int v, char c, map<int, vector<int>> mp_1)
{
	Edge* p = dfa.NodeTable[v].adj;
	int node;
	//�����鿴�ý���Ƿ��иñ�
	while (p != NULL)
	{
		if (p->dest == c)
		{
			node = p->nextVertex;
			break;
		}
		p = p->link;
	}
	//����ý��û�иñߣ�����������������ģ��ý���ڸ÷�����ָ����Լ��ļ���
	//�����nodeΪ���������ڽ�����Ĵ�����������������
	if (p == NULL)
		node = v;
	//�������Ϲ�����ȷ���ý�㣬�����ﷵ�ظý�����ڵļ��ϣ���ָ��ĳ��vector<int>��ָ�룩
	int q; //���ڼ�������ʱ���е����
	for (int i = 0; i < mp_1.size(); i++)
	{
		for (int j = 0; j < mp_1[i].size(); j++)
		{
			if (mp_1[i][j] == node)
				q = i;
		}
	}
	return q;
}

bool nfaManager::if_equal(int v1, int v2, vector<char> chars, map<int, vector<int>> mp_1)
{
	for (int i = 0; i < chars.size(); i++)
	{
		if (dfa_transform(v1, chars[i], mp_1) != dfa_transform(v2, chars[i], mp_1))
			return false;
	}
	return true;
}

void nfaManager::minimize_dfa()
{
	vector<int> s1, s2;//�Ȱ����е�dfa��㻮��Ϊ�����Ӽ�����̬��s1���ͷ���̬��s2��
	for (int i = 0; i < dfa.numVertices; i++)
	{
		if (dfa.NodeTable[i].final == 1)
			s1.push_back(dfa.NodeTable[i].data);
		else
			s2.push_back(dfa.NodeTable[i].data);
	}

	queue<vector<int> > states;  // �ȴ����ֵ�״̬���϶���
	states.push(s1);             //�ȴ�����̬��
	states.push(s2);

	//����ӳ�������������ʱ���ֵĽ���dfa��㼯����ӳ���ϵ�ģ���ʱ��
	map<int, vector<int>> mp_1;
	int ct_1 = 0;
	mp_1[ct_1++] = s1;
	mp_1[ct_1++] = s2;

	//����ӳ�����������mini_dfa����dfa��㼯����ӳ���ϵ�ģ����ս����
	map<int, vector<int>> mp;
	int ct = 0;

	int pop_num = -1;    //������Ϊ�˼�¼��ʱ����ļ�������ʱ���е���ţ�һ���ü�����Ҫ���֣���Ҫ����ʱ����ɾ�����ü���

	//��ʼ����״̬����
	while (!states.empty())
	{

		vector<int> s = states.front();
		states.pop();
		pop_num++;

		if (s.empty()) continue;
		int sz = s.size();
		if (sz == 1)
		{
			mp[ct++] = s;
			continue;
		}

		//�����ռ�dfa������
		vector<vector<int>> sub_states;

		int node = s[0];
		bool ok = true;

		for (int i = 1; i < sz; i++)
		{
			//�����������ת�������һ��������Ҫ����
			if (if_equal(s[i], node, chars, mp_1) == false)
			{
				int cur_node = s[i];
				vector<int> cur_states;
				for (vector<int>::iterator it = s.begin() + 1; it != s.end();)
				{
					if (if_equal(*it, cur_node, chars, mp_1))
					{
						cur_states.push_back(*it);  // �����¼���
						it = s.erase(it);            // ��s��ɾ���ý��, ����itΪԭit����һ��λ��
					}
					else
						++it;
				}
				sub_states.push_back(cur_states);
				// ��Ϊ��ɾ��s�е�Ԫ�أ�Ҫ����һ��s�Ĵ�С
				sz = s.size();
				ok = false;
			}
		}
		if (ok)
		{
			mp[ct++] = s;
			//�����־
			//cout << "this is a test:" << endl;
			//cout << ct - 1;
			//for (int r = 0; r < mp[ct - 1].size(); r++)
			//{
			//	cout << mp[ct - 1][r] << " ";
			//}
			cout << endl;
		}
		else
		{
			sub_states.push_back(s);
			for (int l = 0; l < sub_states.size(); l++)
			{
				states.push(sub_states[l]);
				mp_1[ct_1++] = sub_states[l];
			}
			vector<int> n;
			mp_1[pop_num] = n;
		}
	}

	//������ս����mini_dfa����dfa��㼯��ӳ���mp����һ��������С��dfa

	//������mini_dfa�в�����
	for (int i = 0; i < mp.size(); i++)
	{
		mini_dfa.insertVertex();
	}

	//������mini_dfa�в����
	for (int i = 0; i < mp.size(); i++)
	{
		//����ÿ��mini_dfa�������Ӧ��dfa��㼯�еĽ��
		for (int j = 0; j < mp[i].size(); j++)
		{
			//����ÿ��dfa���ı�
			Edge* p = dfa.NodeTable[mp[i][j]].adj;
			while (p != NULL)
			{
				//Ȼ����Ҫ����ÿ��mini_dfa�������Ӧ��dfa��㼯�еĽ�㣬���ҳ��ý�������ӵĽ�����ڵ�mini_dfa
				for (int k = 0; k < mp.size(); k++)
				{
					vector<int>::iterator it;
					it = std::find(mp[k].begin(), mp[k].end(), p->nextVertex);
					if (it != mp[k].end())
					{
						mini_dfa.insertEdge(i, k, p->dest);
						break;
					}
				}
				p = p->link;
			}
		}
	}

	//�ٴα���mini_dfa�Ľ�㣬�����Ϊ��ȷ����ʼ���ͽ��ܽ��
	for (int i = 0; i < mp.size(); i++)
	{
		for (int j = 0; j < mp[i].size(); j++)
		{
			if (dfa.NodeTable[mp[i][j]].data == 0)
				start_state_dfa = i;
			if (dfa.NodeTable[mp[i][j]].final == 1)
				mini_dfa.NodeTable[i].final = 1;
		}
	}

}

void nfaManager::show_mini_dfa()
{
	ofstream out("output_mini_dfa.txt");
	out << "digraph abc{"<<endl;

	for (int i = 0; i < mini_dfa.numVertices; i++)
	{
		//����ʾ��������
		out << mini_dfa.NodeTable[i].data << "[fontcolor=white][shape=circle];" << endl;
	}

	//��ʼ�����ʾ��begin�������﷢�ֻ�ͼ�������ĻḲ��ǰ���
	out << dfa.NodeTable[start_state_dfa].data << "[fontcolor=black][label=begin];" << endl;

	for (int i = 0; i < mini_dfa.numVertices; i++)
	{
		//���ܽ����ʾ��acc��
		if (mini_dfa.NodeTable[i].final == 1)
			out << mini_dfa.NodeTable[i].data << "[fontcolor=black][label=acc];" << endl;

		Edge* p = mini_dfa.NodeTable[i].adj;
		while (p != NULL)
		{
			out << mini_dfa.NodeTable[i].data << "->" << p->nextVertex << "[label=" << p->dest << "];" << endl;
			p = p->link;
		}
	}
	out << "}";
	out.close();
}

//����С��dfa���ɶ�Ӧ�Ĵʷ���������C����������
//����С��DFAͼ����㿪ʼ�������ɣ�DFA�ĳ�̬������㣩һ����Ψһ�ģ�������̬�����յ��㣩�����ж��������������ʽ��a|b��)��
//���б����������ָ���Լ�����while��䣬�ȷ���while��䣬Ȼ�����ÿ��ǰ���߶���if-else���
//�˺���ͨ��һ��һ������������C��������ÿһ����һ���ַ����洢��������Щ�ַ����洢��vector<string>���͵�lines�У����������������Щ���д��txt�ļ�
void nfaManager::getCcode(int v, vector<string>& lines)
{
	if (mini_dfa.NodeTable[v].final == 1)
		lines.push_back("Done();");

	//����vΪ���ڴ���Ľ����mini_dfaͼ�е����
	//�����ռ����v�ж�����ָ���Լ��ıߣ��ռ��ñߵ�dest
	vector<char> while_char, if_char;
	Edge* p = mini_dfa.NodeTable[v].adj;
	while (p != NULL)
	{
		if (p->nextVertex == v)
			while_char.push_back(p->dest);
		else
			if_char.push_back(p->dest);
		p = p->link;
	}

	if (!while_char.empty()) //�����Ϊ�գ�˵������ָ������ıߣ����ʱ���Ҫ����while���
	{
		//���ɵĶ�ӦC���Է��������׾�Ϊ��char ch=getChar()��
		lines.push_back("char ch = getChar();");
		string line = "while(";
		int i = 0;
		for (; i < while_char.size() - 1;)
		{
			line += "ch ==";
			string str;
			str += while_char[i];
			line += str + "||";
			i++;
		}
		string str;
		str += while_char[i];
		line += "ch ==" + str + ")";
		lines.push_back(line);
		lines.push_back("{");
		if (mini_dfa.NodeTable[v].final == 1)
			lines.push_back("Done();");
		lines.push_back("ch = getChar();");
		lines.push_back("}");
		if (if_char.empty())
		{
			lines.push_back("error;");
		}
	}

	//������ָ��������ıߺ󣬽����ﴦ��ָ���Ľ��ı�
	if (!(if_char.empty()))
	{
		if (while_char.empty())
			lines.push_back("char ch = getChar();");
		Edge* q = mini_dfa.NodeTable[v].adj;
		if (q != NULL)
		{
			while (q != NULL)
			{
				if (q->nextVertex != v)
				{
					string line_1 = "if( ch ==";
					string str_2;
					str_2 += q->dest;
					line_1 += str_2 + ")";
					lines.push_back(line_1);
					lines.push_back("{");
					getCcode(q->nextVertex, lines);
					lines.push_back("}");
					lines.push_back("else");
				}
				q = q->link;
			}
			string str_3;
			str_3 += 48 + v;
			string line_2;
			line_2 += "error(" + str_3 + ");";
			lines.push_back(line_2);
		}
	}
}

void nfaManager::show_code()
{
	vector<string> lines;
	getCcode(mini_dfa.NodeTable[start_state_dfa].data, lines);

	ofstream out("Ccode.txt");
	for (int i = 0; i < lines.size(); i++)
	{
		out << lines[i] << endl;
	}
	out.close();

}