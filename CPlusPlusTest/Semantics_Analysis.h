#pragma once
#pragma once
#include "Grammar_Analysis.h"
#include <unordered_map>


class Semantics_Analysis : public Grammar_Analysis
{
protected:
	typedef void (Semantics_Analysis::* function_pointer)(string s);				    //����ָ���ض���
	typedef unordered_map<string, Semantics_Analysis::function_pointer> function_map;   //���嶯����ִ�к����Ĺ���ͼ
	function_map get_semantic_action;													//�������嶯���������
	function_pointer semantic_action = NULL;     										//ִ�����嶯������

	struct token_property
	{
		string token_word;			   //token����
		string token_type;			   //token��������
		int token_number = 0;		   //token���ڱ��ڵ����
		string* token_pointer = NULL;  //���token�Ǳ�ʶ������ָ��ָ����ű�
	} ;				   //token����������
	vector<token_property> token_list; //token��Ϣ�б�

	/*����������̣���д���ű�*/
	unordered_map<string, int> end_symbol_name_to_number;//�õ�һ����ʶ���ڱ��е�λ��
	vector<type_property> type_property_list;//�������
	vector<array_table> array_table_list;//�������
	vector<structure_table> structure_table_list;//�ṹ����
	vector<function_table> function_table_list;//��������
	vector<constant_table> constant_table_list;//��������
	vector<symbol_table> symbol_table_list;//ÿ�ŷ��ű���

	/*����������̣�������Ԫʽ*/
	vector<vector<string>> semantics_property;					//�����ķ�
	stack<string> semantic_data_stack;							//��������ջ
	stack<string> temp_semantic_data_stack;						//���嶯�������ݴ�ջ����Ӧ�����嶯�������﷨ջ
	vector<four_item_production> four_item_production_list;     //��Ԫʽ����
	vector<vector<four_item_production>> function_four_item_production_list;//ÿ������ִ�в�������Ԫʽ����
	int temp_t_number = 1;//��ʱ����xt��xֵ

public:
	Semantics_Analysis();
	~Semantics_Analysis();
	vector<vector<four_item_production>> return_four_item_production() { return this->function_four_item_production_list; }//�õ����ɵ���Ԫʽ����
	vector<symbol_table> return_symbol_table_list() { return this->symbol_table_list; }//�õ����ű��ܱ�ļ���
	vector<string> return_const_symbol() { return this->const_symbol; }
	void scanf_information();
	void Generate_Token(); //��������Ĵʷ�����
	void check_code(int state, string token);
	int search_key(string one_token, vector<string>* key, string type);
	void print_token_list();
	int LL1_sentence_analysis(string token);//����������﷨����+�������
	int LL1_sentence_analysis_with_print_step(string token);
	void add_production(string operate);
	void add_production(string operate, string first);
	void push_token(string s);
	void handle_token(string s);
	void handle_equal_symbol(string s);
	void handle_begin_if(string s);
	void handle_else_if(string s);
	void handle_else(string s);
	void handle_end_if(string s);
	void handle_judge_end(string s);
	void handle_begin_while(string s);
	void handle_do_while(string s);
	void handle_end_while(string s);
	void handle_compare_symbol(string s);
	void handle_array(string s);
	void handle_begin_for(string s);
	void handle_judge_for(string s);
	void handle_do_for(string s);
	void handle_end_for(string s);
	void handle_after_do(string s);
	void handle_auto_change(string s);
	void handle_break(string s);
	void handle_continue(string s);
	void handle_switch(string s);
	void handle_case(string s);
	void handle_case_end(string s);
	void handle_default(string s);
	void handle_switch_end(string s);
	void handle_function_begin(string s);  //������ʼ
	void handle_function_end(string s);  //��������
	void handle_program_end(string s);   //�������
	void handle_jump_function(string s);  //��ת��ִ�к���������Ԫʽ���з���ֵ��
	void handle_jump_void_function(string s); //��ת��ִ�к���������Ԫʽ���޷���ֵ��
	void handle_return(string s);      //return��Ԫʽ
	void handle_dian_symbol(string s);   //����������
	void handle_before_identifier(string s); ////��ʶ��ǰ׺��Ԫʽ��ָ��*��ȡ��ַ&��

	int jilu_symbol_num = 0;//������¼�����ķ��ű�
	void save_assignment(string s);
	void save_array_assignment(string s);
	void save_array(string s);
	void save_struct(string s);
	void save_struct_name(string s);
	void save_struct_member(string s);
	void write_symbol_table(string s);
	void write_struct_table(string s);
	void write_function_table(string s);
	int duplicate_check(string s);
};