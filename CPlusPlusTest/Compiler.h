#pragma once
#include <bits/stdc++.h>
#define token_max_size 1000            //����token������󳤶�

using namespace std;

/*�м����Ķ��壬ǰ�����˼�Ľӿ�*/

/*���ű�Ķ���*/
struct type_property
{
	string type_code;				  //������룬�硰���Ρ��������͡�
	void* information_pointer = NULL; //��Ϣ��ָ�룬������������ָ��NULL������ָ�������ȵ�
} ;					  //�������������

struct array_table
{
	int bottom_number;//�����½�
	int top_number;//�����Ͻ�
	type_property* type_information;//������ÿ��Ԫ�صĵ�������Ϣ����int a[10]ָ��int��int a[5][10]ָ����泤��Ϊ10�����飬
	int array_size;//���鳤��
} ;//�����

typedef struct constant_table
{
	union {
		int int_value;
		float float_value;
		double double_value;
		char char_value;
		bool bool_value;
	}value;
	constant_table* next = NULL;
} constant_table; //������

struct structure_property
{
	string ID;			//�ṹ���б����ı�ʶ��	
	int OFF = 0;			//ƫ����
	type_property* tp = NULL;//����ָ��
	constant_table* vp = NULL;//��ֵָ�룬ָ������
} ;//�ṹ���������

typedef struct
{
	int num;			//�ṹ���ڱ�������
	int total_OFF;       //��ƫ����
	vector<structure_property> structure_property_list; //�ṹ�������
} structure_table;//�ṹ��

typedef struct
{

} function_table;//������

struct symbol_property
{
	string name;		  //��ʶ��������
	type_property* type = NULL;   //��ʶ��������ָ��
	string kind;		  //��ʶ����������Ϣ���硰��������������
	int offset = 0;//��ַƫ����
	void* address = NULL; //��ʶ���Ľ��ͱ��糣��ָ����������ָ������
};		  //���ű��ܱ���ÿ����ʶ��������

typedef struct symbol_table
{
	symbol_table* header = NULL;                   //ÿ�ź������ű��ͷָ�룬ָ����øú����ĺ����ķ��ű�
	int total_size = 0;                            //һ�ź������ű���ܳ���
	vector<symbol_property> symbol_property_table; //���ű��ܱ���ÿ����ʶ�������Ա�
} symbol_table;                                    //���ű�ÿ������һ�ŷ��ű�

/*��Ԫʽ�Ķ���*/
struct first_operation_target
{
	string operation_target_value;  //��һ����������ֵ
	bool active_information = false;//��һ���������Ļ�Ծ��Ϣ
} ;		    //��һ��������������

struct second_operation_target
{
	string operation_target_value;  //�ڶ�����������ֵ
	bool active_information = false;//�ڶ����������Ļ�Ծ��Ϣ
} ;		    //�ڶ���������������

struct operation_result
{
	string result_target_value;     //�����������ֵ
	bool active_information = false;//����������Ļ�Ծ��Ϣ
} ;				    //���������������

struct four_item_production
{
	string basic_block_flag = "˳���";    //��ǻ��������ڻ����
	string operation_symbol;			   //�������
	first_operation_target first_target;   //��һ��������������
	second_operation_target second_target; //�ڶ���������������
	operation_result result_target;		   //������������
} ;		               //��Ԫʽ���е�����