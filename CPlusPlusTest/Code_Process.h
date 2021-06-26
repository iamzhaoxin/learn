#pragma once
#include "Compiler.h"
class Code_Process
{
protected:
	struct DAG_property
	{
		int node_number = 0;//������
		vector<int> front_node;//ǰ�����
		vector<int> rear_node;//��̽��
		string operate_symbol;//�����
		string main_mark;//�����
		vector<string> other_mark;//�������
		int symbol_size;//�ýڵ��������ʶ�����ֽڳ��ȣ�1/2
	} ;

	vector<symbol_table> symbol_table_list;//���ű�
	vector<vector<four_item_production>> four_item_production_list;     //��Ԫʽ����
	vector<string> const_symbol = { "\0" };//��ʶ��,��a_b

	/*�����Ż�����*/
	vector<vector<four_item_production>> new_four_item_production_list;     //�Ż������Ԫʽ���� 
	vector<map<string, bool>> symbol_active_list;               //��Ծ��Ϣ��ʼ��ʱ�õ��ĸ�����
	map<string, int> DAG_symbol_to_node;//�õ�һ������������ʱ��������ʱ������DAGͼ����һ���ڵ���
	vector<DAG_property> DAG_map;//DAGͼ
	vector<vector<DAG_property>> out_DAG;//�����DAGͼ
	vector<four_item_production> temp_f;//��ʱ��Ԫʽ��
	four_item_production temp_item;//��ʱ��Ԫʽ

	/*�ṩ����������*/
	vector<map<string, int>> get_symbol_size;//һ����ʶ���ĳ��ȣ��ṩ�����ɴ���ʹ��
	int function_number = -1;//��ǵڼ�������
public:
	Code_Process();
	~Code_Process();
	void get_symbol_table_list(vector<symbol_table> s);
	void get_four_item_production(vector<vector<four_item_production>> f);//��Ԫʽ��ֵ
	void get_const_symbol(vector<string> c);//��ʶ����ֵ
	vector<string> return_const_symbol() { return this->const_symbol; }
	vector<vector<four_item_production>> return_new_four_item_production_list() { return this->new_four_item_production_list; }//�����Ż������Ԫʽ
	vector<symbol_table> return_symbol_table_list() { return this->symbol_table_list; }
	vector<map<string, int>> return_sizeof_symbol_list() { return this->get_symbol_size; }
	void print_old_four_item_production();//���δ�Ż�����Ԫʽ
	void print_new_four_item_production();//����Ż������Ԫʽ
	void print_DAG_information();//����Ż������в�����DAG
	int block_part_variable_flag(string s);//�ж���Ԫʽ�е�һ����ʶ�������Ƿ��ǿ��ڵľֲ�����
	int const_symbol_flag(string s);//�ж�һ���ַ��ǲ��Ǳ�ʶ��
	int const_number_flag(string s);//�ж�һ���ַ��ǲ��ǳ���
	int get_symbol_number(string s, int i);//�õ�һ����ʶ���ڷ��ű��е�λ��
	void divide_basic_block(vector<vector<four_item_production>>* four_item_production_list);//���ֻ�����
	void initialize_active_information(vector<vector<four_item_production>>* four_item_production_list);//��Ծ��Ϣ��ʼ��ǰ��ʼ��������
	void change_active_information(vector<vector<four_item_production>>* four_item_production_list);//�޸Ļ�Ծ��Ϣ
	void code_optomization();//�����Ż�
	int get_A_B_node_number(string s);//DAG�д���A=B�ĵ�
	int get_A_C1wC2_node_number(string c1, string c2, string c3);//DAG�д���A=C1wC2�ĵ�
	int get_A_wB_node_number(string B, string w);//DAG�д���A=wB�ĵ�
	int get_A_BwC_node_number(string B, string C, string w);//DAG�д���A=BwC�ĵ�
	void handle_main_other_order(int node);//��DAG�е���������ӱ��˳�򽻻�����
	void handle_DAG();//����DAG���ɿ����Ż���Ԫʽ
};