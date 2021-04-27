#include "Grammar.h"

int main()
{
	Grammar g;
	g.init();

	if (g.have_left_recursion())
	{
		cout << "输入产生式中含有左递归式，尝试进行修改" << endl;
		g.first_letter_substitution();
		g.printProduction();
		g.parsing_direct_left_recursion();
		g.del_unreachable_production();
		cout << "提取左递归式后的产生式集合如下：" << endl << endl;
		g.printProduction();
	}

	if (g.have_common_factor())
	{
		cout << "输入产生式中含有左公因式，尝试进行修改" << endl;
		g.first_letter_substitution();
		g.parsing_common_factor();
		g.del_unreachable_production();
		cout << "提取左公因式后的产生式集合如下：" << endl << endl;
		g.printProduction();
	}

	//g.cal_First();
	//
	//g.printFirst();

	//// 计算Follow集合
	//g.cal_Follow();
	//g.printFollow();

	//// 计算Select集合
	//g.cal_Select();
	//g.printSelect();

	//if (g.is_LL1())
	//{
	//	g.printTable();
	//	if (g.grammar_parsing())
	//	{
	//		cout << "句子匹配成功" << endl;
	//	}
	//}
	//else
	//{
	//	cout << "输入文法不是LL(1)型文法" << endl;
	//}

	return 0;
}
