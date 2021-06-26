#pragma once
#include "Compiler.h"

class Lexical_Analysis
{
protected:
	vector<string> token_list;//token������
	vector<string> token_type;//token��������
	vector<int> token_number;//token���ڱ��ڵ����
	vector<string> key_word = { "\0","int", "main", "void","if" , "else", "char",
	"default", "do", "double","const" , "enum", "extern",
	"float", "for", "goto", "continue", "auto", "long",
	"register", "return", "short", "signed", "sizeof", "static",
	"struct", "switch", "typedef", "union", "unsigned", "case",
	"volatile", "while", "include", "printf","string","break","bool" }; //�ؼ���
	vector<string> key_symbol = { "\0","+", "-", "*", "/", "<", "<=", ">", ">=", "=", "==",
		"!=", ";", "(", ")", "^", ",", "\"", "\\'", "#", "&",
		"&&", "|", "||", "%", "~","@","$","?","<<", ">>", "[", "]", "{",
		"}", "//", ".", ":", "!","_","\\","/*","*/","::","->" ,"++","--" }; //�ؼ�����
	vector<string> const_symbol = { "\0" };//��ʶ��,��a_b
	vector<string> const_number = { "\0" };//����,��123
	vector<string> const_char = { "\0" };//�ַ�,��'c'
	vector<string> const_string = { "\0" };//�ַ���,��"def"
	fstream code_file;
public:
	Lexical_Analysis();
	~Lexical_Analysis();
	void Generate_Token();//�ʷ���������
	int change_state(int state, char ch);//״̬ת���Զ���
	void check_code(int state, char* token);//�����ս�̬���ɵ���
	int search_key(char* token, vector<string>* key, string type);//���ҡ�ƥ�䡢����token���кͲ��ұ�
	void print_token_list();//���token����
};