#pragma once
#include "Compiler.h"
#include "Lexical_Analysis.h"
#include "Grammar_Analysis.h"
#include "Semantics_Analysis.h"
#include "Code_Process.h"
#include "Code_Generation.h"

int main()
{
	Lexical_Analysis LA;
	Semantics_Analysis SA;
	Grammar_Analysis GA;
	Code_Process OP;
	Code_Generation CG;
	//LA.Generate_Token();
	//LA.print_token_list();

	
	//GA.scanf_information();//�����ķ��������ķ�
	//GA.get_first_list();//���ķ������ս��first��
	//GA.get_follow_list();//���ķ������ս��follow��
	//GA.get_select_list();//���ķ�������ʽselect��
	//GA.get_LL1_list();//��LL1�﷨����������
	//GA.print_information();//����ķ������Ϣ
	//GA.Generate_Token();//�ʷ�����+�﷨����+�������
	SA.scanf_information();//�����ķ��������ķ�
	SA.get_first_list();//���ķ������ս��first��
	SA.get_follow_list();//���ķ������ս��follow��
	SA.get_select_list();//���ķ�������ʽselect��
	SA.get_LL1_list();//��LL1�﷨����������
	SA.print_information();//����ķ������Ϣ
	SA.Generate_Token();//�ʷ�����+�﷨����+�������
	cout << "----------------------------------------------------------------------------------------"<<endl;
	SA.print_token_list();//����ʷ����������б����token��
	OP.get_const_symbol(SA.return_const_symbol());//��ǰ�����ɵı�ʶ����Ϣ�������
	OP.get_four_item_production(SA.return_four_item_production());//��ǰ�����ɵ��м������Ԫʽ�������
	OP.get_symbol_table_list(SA.return_symbol_table_list());//��ǰ�����ɵķ��ű������
	OP.code_optomization();//DAG���ڴ����Ż�
	OP.print_DAG_information();//���DAG����Ϣ
	OP.print_old_four_item_production();//���δ�Ż�����Ԫʽ��������Ծ��Ϣ
	OP.print_new_four_item_production();//����Ż������Ԫʽ��������Ծ��Ϣ
	CG.get_const_symbol(OP.return_const_symbol());//��ʶ����ֵ
	CG.get_intermediate_code_list(OP.return_new_four_item_production_list());//�Ż�����м���븳ֵ
	CG.get_symbol_table_list(OP.return_symbol_table_list());//���ű�ֵ
	CG.get_sizeof_symbol_list(OP.return_sizeof_symbol_list());//���Ŷ�Ӧ�ĳ��ȸ�ֵ
	CG.generate_code_list();//����Ŀ�����
	CG.write_in_file();
	system("pause");
}