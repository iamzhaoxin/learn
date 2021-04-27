#include "Grammar.h"

int main()
{
	Grammar g;
	g.init();

	if (g.have_left_recursion())
	{
		cout << "�������ʽ�к�����ݹ�ʽ�����Խ����޸�" << endl;
		g.first_letter_substitution();
		g.printProduction();
		g.parsing_direct_left_recursion();
		g.del_unreachable_production();
		cout << "��ȡ��ݹ�ʽ��Ĳ���ʽ�������£�" << endl << endl;
		g.printProduction();
	}

	if (g.have_common_factor())
	{
		cout << "�������ʽ�к�������ʽ�����Խ����޸�" << endl;
		g.first_letter_substitution();
		g.parsing_common_factor();
		g.del_unreachable_production();
		cout << "��ȡ����ʽ��Ĳ���ʽ�������£�" << endl << endl;
		g.printProduction();
	}

	//g.cal_First();
	//
	//g.printFirst();

	//// ����Follow����
	//g.cal_Follow();
	//g.printFollow();

	//// ����Select����
	//g.cal_Select();
	//g.printSelect();

	//if (g.is_LL1())
	//{
	//	g.printTable();
	//	if (g.grammar_parsing())
	//	{
	//		cout << "����ƥ��ɹ�" << endl;
	//	}
	//}
	//else
	//{
	//	cout << "�����ķ�����LL(1)���ķ�" << endl;
	//}

	return 0;
}
