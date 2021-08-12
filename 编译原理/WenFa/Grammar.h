#pragma once
#include <iostream>
#include <string>
#include <set>
#include <map>
#include <vector>
using namespace std;

// 产生式结构体
struct pstring {
	char left;
	string right;
};

class Grammar
{
public:
	Grammar();
	// 拷贝构造函数
	Grammar(const Grammar&);
	// 用户输入初始化文法
	void init();
	// 初始化产生式集合
	void init_P();
	// 将产生式右侧第一个字符为非终结符的进行代入替换
	void first_letter_substitution();
	// 将不可达的产生式删除
	void del_unreachable_production();
	// 提取左公因式
	void parsing_common_factor();
	// 消除直接左递归
	void parsing_direct_left_recursion();
	// 消除间接左递归
	void parsing_indirect_left_recursion();
	// 计算First集合
	void cal_First();
	// 计算Follow集合
	void cal_Follow();
	// 调用递归方法计算Follow集合
	void cal_Follow_recur();
	// 计算Select集合
	void cal_Select();
	// 生成预测分析表，当同一单元格有多个式子时，返回false
	bool get_Table();
	// 判断是否为LL(1)文法
	bool is_LL1();
	// 预测分析法进行语法分析
	bool grammar_parsing();
	// 对某个非终结符求First集合
	void get_First_recur(char);
	// 不递归的求出Follow集合
	void get_Follow();
	// 对某个非终结符求Follow集合
	void get_Follow_recur(char, int);
	// 判断是否为终结符
	bool inVt(char);
	// 判断是否为非终结符
	bool inVn(char);
	// 判断产生式是否存在左公因式
	bool have_common_factor();
	// 判断产生式是否存在左递归式
	bool have_left_recursion();
	// 求终结符在预测分析表中的列标
	int index_in_terminal(char);
	// 求非终结符在预测分析表中的行标
	int index_in_nonterminal(char);
	// 输出产生式集
	void printProduction();
	// 输出First集
	void printFirst();
	// 输出Follow集
	void printFollow();
	// 输出Select集
	void printSelect();
	// 输出预测分析表
	void printTable();
	~Grammar();

private:
	// 终结符集，小写字母
	set<char> Vt;
	// 非终结符集，大写字母
	set<char> Vn;
	// 产生式集合P
	vector<pstring> P;
	// 产生式中包含的终结符集
	set<char> terminal;
	// 产生式中包含的非终结符集
	set<char> nonterminal;
	// First集合
	map<char, set<char>> first_set;
	// Follow集合
	map<char, set<char>> follow_set;
	// Select集合
	vector<set<char>> select_set;
	// 预测分析表
	int** predict_table;
	// 产生式数量
	int pnum;
	// 开始符号
	char S;
	// LL(1)文法标志
	bool LL1;
};
