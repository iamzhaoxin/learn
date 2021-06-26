#pragma once
#include "Lexical_Analysis.h"

class Grammar_Analysis : public Lexical_Analysis
{
protected:
	vector<int>::size_type production_number;					 //����ʽ����
	string head_symbol;						 //�ķ��ĳ�ʼ����
	vector<string> production_left;			 //�ķ�����ʽ��
	vector<vector<string>> production_right; //�ķ�����ʽ�Ҳ�
	vector<vector<string>> first_list;		 //first������1��ʼ����
	vector<vector<string>> follow_list;		 //follow������1��ʼ����
	vector<vector<string>> select_list;		 //select������1��ʼ����
	vector<vector<string>> LL1_list;		 //LL1��������1��ʼ����
	stack<string> grammar_stack;			 //�﷨����ջ

	//�ս���ͷ��ս����
	vector<string> end_symbol = { "\0", "+", "-", "*", "/", "(", ")","[","]","#","void","main","{","}","int","float","char","string","double","bool","break",
					  "&&","||","(",")",",",">=" ,"<=" ,"==" ,"!=","<",">","while" ,"if","else","for","=","++","--","��ʶ��",";","��ֵ","continue","switch",".",
					  "case","default",":","�ַ�","�ַ���","struct","return","*","&" };
	vector<string> not_end_symbol = { "\0","����","��������","����˵��","��������","�����б�", "����", "�������","�������","��ʶ����ֵ","��������","������ֵ���",
						  "��ֵ���", "�����ֵ","���ʽ���","���ʽ","���ʽ��","���ʽһ","���ʽ�Ҷ�","���ʽ��","�������","����ִ��","������ֵ","��������","�����ʼ��ֵ",
						  "������","�������ж�","ѭ�����","�ж����","�жϻ����","�жϻ�","�ж������","�ж���","�ȽϷ�","�ǿո������","�ղ���ʽ","��ʼ����","��������",
						  "���������ֵ","��ת���","����ѡ��","������ʶ��","����������","���������","��ʶ����׺","��ֵ��ʶ��","��ֵ��ʶ����׺","��Ŀ������","�ṹ�帴�����",
						  "�ṹ���ʶ��","�����","�������","��ʶ��ǰ׺","��������","��ֵ���ʽ","����ʽ" };

public:
	Grammar_Analysis();
	~Grammar_Analysis();
	void Generate_Token();																	  //�﷨�����еĴʷ�����
	void get_first_list();																	  //��first��
	void get_follow_list();																	  //��follow��
	void get_select_list();																	  //��select��
	void get_LL1_list();																	  //��LL1������
	int LL1_sentence_analysis(string s);													  //�﷨�����е�LL1�﷨����;
	void scanf_information();																  //������Ϣ
	void print_information();																  //�����Ϣ
	int get_not_end_symbol_number(string s);												  //�õ����ս���ڲ��ұ��е�λ��
	int get_end_symbol_number(string s);													  //�õ��ս���ڲ��ұ��е�λ��
	string get_next_w_type(string s);														  //����һ�������ַ���������
	int new_symbol_flag(vector<vector<string>>* symbol, string s, vector<char>::size_type i); //�ж�s�Ƿ�����ڣ�*symbol��[i]��
	int empty_symbol_flag(string s);														  //�ж�s�Ƿ���Ƴ��ղ���ʽ
};