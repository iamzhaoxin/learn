#include "Code_Process.h"

class Code_Generation :public Code_Process
{
private:
	vector<vector<four_item_production>> intermediate_code_list;//�Ż�����м����
	vector<vector<string>> object_code_list;//���ɵ�Ŀ�����

	vector<string> reg_8 = { "AL", "AH", "BL", "BH", "CL", "CH","DL", "DH" };//8λ�Ĵ���
	vector<string> reg_16 = { "AX", "BX", "CX", "DX" };//16λ�Ĵ���
	map <string, string>symbol_to_reg;//�������ڵļĴ���
	map <string, string>reg_to_symbol;//�Ĵ�����ı���
public:
	Code_Generation();
	~Code_Generation();
	void get_const_symbol(vector<string> c) { this->const_symbol = c; }
	void get_symbol_table_list(vector<symbol_table> s) { this->symbol_table_list = s; }
	void get_intermediate_code_list(vector<vector<four_item_production>> f) { this->intermediate_code_list = f; }
	void get_sizeof_symbol_list(vector<map<string, int>> g) { this->get_symbol_size = g; }
	void generate_code_list();//����Ŀ�����
	void write_in_file();//д���ļ�
};