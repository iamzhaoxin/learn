#include <iostream>
#include <string>
#include "nfamanager.h"
#include <vector>
#include<fstream>
using namespace std;

int chars(string s)
{
	int n = 0;
	for (int i = 0; i < s.size(); i++)
	{
		if ((s[i] >= 48 && s[i] <= 57) || (s[i] >= 65 && s[i] <= 90)
			|| (s[i] >= 97 && s[i] <= 122))
			n++;
	}
	return n;
}

int main() {
	nfaManager NFA;
	string s;
	//cin >> s;
	ifstream in("input.txt");
	in >> s;

	//将正则表达式转化为nfa
	s = NFA.insert_concat(s);
	s = NFA.regexp_to_postfix(s);
	NFA.postfix_to_nfa(s);
	//将nfa转化为dfa
	set<int> si;
	NFA.nfa_to_dfa(si);
	//最小化dfa
	NFA.minimize_dfa();

	NFA.show_nfa();
	NFA.show_dfa();
	NFA.show_mini_dfa();
	NFA.show_code();
}
