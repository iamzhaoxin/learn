#include "Code_Generation.h"

Code_Generation::Code_Generation()
{
}

Code_Generation::~Code_Generation()
{
}

void Code_Generation::generate_code_list()//����Ŀ�����
{
	vector<string> line_code;//һ��Ŀ�����
	function_number = -1;
	for (int i = 0; i < intermediate_code_list.size(); i++)
	{//�鿴һ���������Ż����м����
		function_number++;
		object_code_list.resize(object_code_list.size() + 1);
		object_code_list[i].push_back("DATA\tSEGMENT");//���ݶο�ʼ
		for (int j = 0; j < symbol_table_list[i].symbol_property_table.size(); j++)
		{
			string temp_s;
			if (symbol_table_list[i].symbol_property_table[j].type->type_code == "int")
			{//int��ΪDBһ�ֽ�
				constant_table* value = (constant_table*)symbol_table_list[i].symbol_property_table[j].address;
				if (value == NULL)
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t?";
				}
				else
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t" + to_string(value->value.int_value);
				}
				//free(value);
			}
			else if (symbol_table_list[i].symbol_property_table[j].type->type_code == "double")
			{//double��ΪDW˫�ֽ�
				constant_table* value = (constant_table*)symbol_table_list[i].symbol_property_table[j].address;
				if (value == NULL)
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDW\t?";
				}
				else
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDW\t" + to_string(value->value.double_value);
				}
				//free(value);
			}
			else if (symbol_table_list[i].symbol_property_table[j].type->type_code == "float")
			{//float��ΪDBһ�ֽ�
				constant_table* value = (constant_table*)symbol_table_list[i].symbol_property_table[j].address;
				if (value == NULL)
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t?";
				}
				else
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t" + to_string(value->value.float_value);
				}
				//free(value);
			}
			else if (symbol_table_list[i].symbol_property_table[j].type->type_code == "char")
			{//char��ΪDBһ�ֽ�
				constant_table* value = (constant_table*)symbol_table_list[i].symbol_property_table[j].address;
				if (value == NULL)
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t?";
				}
				else
				{
					temp_s = symbol_table_list[i].symbol_property_table[j].name + "\tDB\t" + to_string(value->value.char_value);
				}
				//free(value);
			}
			object_code_list[i].push_back(temp_s);//һ������������
		}
		object_code_list[i].push_back("DATA\tENDS");//���ݶν���
		object_code_list[i].push_back("CODE\tSEGMENT");//���ݶο�ʼ
		object_code_list[i].push_back("ASSUME\tCS:CODE,DS:DATA");//���ݶο�ʼ
		object_code_list[i].push_back("START:\tMOV AX,DATA");
		object_code_list[i].push_back("\tMOV DS,AX");//���û�ַ
		string temp_code;
		int part_number = 0;
		stack<string> temp_position;
		string for_jump_position;
		string switch_jump_position;
		for (__int64 j = 0; j < intermediate_code_list[i].size(); j++)
		{//������һ��������Ԫʽ���ÿһ��
			if (intermediate_code_list[i][j].basic_block_flag == "��ڿ�")
			{
				symbol_to_reg.clear();
				reg_to_symbol.clear();//�ͷżĴ���
			}

			/*������䲿��*/
			if ((intermediate_code_list[i][j].operation_symbol == "beginFunction") || (intermediate_code_list[i][j].operation_symbol == "endFunction"))
			{//�����Ŀ�ͷ�ͽ�β��������������
				continue;
			}
			if ((intermediate_code_list[i][j].operation_symbol == "beginDo") || (intermediate_code_list[i][j].operation_symbol == "endDo"))
			{//��������������
				continue;
			}

			/*if������*/
			else if ((intermediate_code_list[i][j].operation_symbol == "ifJudge") || (intermediate_code_list[i][j].operation_symbol == "ifEndJudge"))
			{//if�ж��е��ж���ת���
				object_code_list[i].push_back(temp_code);
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "ifBeginJudge") || (intermediate_code_list[i][j].operation_symbol == "else"))
			{//if�ж��е���ת�����
				string s = temp_position.top();
				temp_position.pop();
				object_code_list[i].push_back(s + ":\t");
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "ifBeginDo") || (intermediate_code_list[i][j].operation_symbol == "ifToEnd") || (intermediate_code_list[i][j].operation_symbol == "endIf"))
			{//if�ж��е�˳��ִ�����
				continue;
			}

			/*while������*/
			else if ((intermediate_code_list[i][j].operation_symbol == "whileDoIf"))
			{//while�ж��е���ת���
				object_code_list[i].push_back(temp_code);
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "endWhile"))
			{//while�ж��е���ת�������
				string s = temp_position.top();
				temp_position.pop();
				object_code_list[i].push_back(s + ":\t");
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "beginWhile") || (intermediate_code_list[i][j].operation_symbol == "whileBeginDo"))
			{//while�ж��е�˳��ִ�����
				continue;
			}

			/*for������*/
			else if (intermediate_code_list[i][j].operation_symbol == "forDoIf")
			{//for�ж��е���ת���
				object_code_list[i].push_back(temp_code);
				continue;
			}
			else if (intermediate_code_list[i][j].operation_symbol == "forBeginJudge")
			{//for�ж���ת���ж�����
				for_jump_position = ("FUN" + to_string(part_number));
				object_code_list[i].push_back("FUN" + to_string(part_number) + ":");
				part_number++;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "endFor"))
			{//for�ж��е���ת�������
				object_code_list[i].push_back("\tJMP " + for_jump_position);
				string s = temp_position.top();
				temp_position.pop();
				object_code_list[i].push_back(s + ":\t");
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "afterForDo") || (intermediate_code_list[i][j].operation_symbol == "forBegin") || (intermediate_code_list[i][j].operation_symbol == "forBeginEnd"))
			{//for�ж��е�˳��ִ�����
				continue;
			}

			/*switch������*/
			else if (intermediate_code_list[i][j].operation_symbol == "switch")
			{
				switch_jump_position = "FUN" + to_string(part_number);
				part_number++;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "case"))
			{//switch�ж��е���ת���
				object_code_list[i].push_back(temp_code);
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "caseEnd"))
			{//switch�ж��е���ת�������
				string s = temp_position.top();
				temp_position.pop();
				object_code_list[i].push_back(s + ":\t");
				continue;
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "caseBegin") || (intermediate_code_list[i][j].operation_symbol == "default") || (intermediate_code_list[i][j].operation_symbol == "caseBeginJudge"))
			{//switch�ж��е�˳��ִ�����
				continue;
			}
			else if (intermediate_code_list[i][j].operation_symbol == "break")
			{
				object_code_list[i].push_back("\tJMP " + switch_jump_position);
				continue;
			}
			else if (intermediate_code_list[i][j].operation_symbol == "endSwitch")
			{
				object_code_list[i].push_back(switch_jump_position + ":");
			}

			/*��ֵ���ʽ����*/
			else if (intermediate_code_list[i][j].operation_symbol == "=")
			{//��ֵ���ʽ(= B _ A)
				if (symbol_to_reg.count(intermediate_code_list[i][j].first_target.operation_target_value))
				{//��ǰ�мĴ����洢B�ı���
					if (intermediate_code_list[i][j].first_target.active_information)
					{
						//object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(intermediate_code_list[i][j].first_target.operation_target_value, i)].offset) + "]," + intermediate_code_list[i][j].first_target.operation_target_value);
						////MOV DS:[B.offset],B
						object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + "," + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value]);
						//MOV A,reg_B
						continue;
					}
				}
				else if (symbol_to_reg.count(intermediate_code_list[i][j].result_target.result_target_value))
				{//��ǰ�мĴ����洢A�ı���
					object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
					//MOV reg_A,B
					object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + "," + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value]);
					//MOV A,reg_A
					continue;
				}
				else
				{//��û�мĴ�������A,Ҳû�мĴ�������B
				 //�鿴�Ƿ��мĴ������У��п���ʹ�ÿ���
					if (get_symbol_size[function_number][intermediate_code_list[i][j].result_target.result_target_value] == 8)
					{//double����Ҫ16λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_16.size(); k++)
						{
							if (!reg_to_symbol.count(reg_16[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_16[k], intermediate_code_list[i][j].result_target.result_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].result_target.result_target_value, reg_16[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								//MOV reg_A,B
								object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + "," + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value]);
								//MOV A,reg_A
								flag = 1;
								break;
							}
						}
						if (flag)//����ɹ���������ѭ��
							continue;
					}
					else
					{//��������ֻ��Ҫ8λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_8.size(); k++)
						{
							if (!reg_to_symbol.count(reg_8[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_8[k], intermediate_code_list[i][j].result_target.result_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].result_target.result_target_value, reg_8[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								//MOV reg_A,B
								object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + "," + symbol_to_reg[intermediate_code_list[i][j].result_target.result_target_value]);
								//MOV A,reg_A
								flag = 1;
								break;
							}
						}
						if (flag)//����ɹ���������ѭ��
							continue;
					}
					if (get_symbol_size[function_number][intermediate_code_list[i][j].result_target.result_target_value] == 8)
					{//û�п��мĴ��������豣��Ĵ�����ֵ
						if ((get_symbol_number(reg_to_symbol["AX"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AX"], i)].offset) + "]," + reg_to_symbol["AX"]);
							symbol_to_reg.erase(reg_to_symbol["AX"]);
							reg_to_symbol["AX"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AX", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AX"));
						object_code_list[i].push_back("\tPUSH AX");
						object_code_list[i].push_back("\tMOV AX," + intermediate_code_list[i][j].first_target.operation_target_value);
						object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + ",AX");
						object_code_list[i].push_back("\tPOP AX");
						continue;
					}
					else
					{
						if ((get_symbol_number(reg_to_symbol["AL"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AL"], i)].offset) + "]," + reg_to_symbol["AL"]);
							symbol_to_reg.erase(reg_to_symbol["AL"]);
							reg_to_symbol["AL"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AL", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AL"));
						object_code_list[i].push_back("\tPUSH AL");
						object_code_list[i].push_back("\tMOV AL," + intermediate_code_list[i][j].first_target.operation_target_value);
						object_code_list[i].push_back("\tMOV " + intermediate_code_list[i][j].result_target.result_target_value + ",AX");
						object_code_list[i].push_back("\tPOP AL");
						continue;
					}
				}
			}

			/*�Ƚ�����*/
			else if ((intermediate_code_list[i][j].operation_symbol == "==") || (intermediate_code_list[i][j].operation_symbol == "!=") || (intermediate_code_list[i][j].operation_symbol == "<=") || (intermediate_code_list[i][j].operation_symbol == ">=") || (intermediate_code_list[i][j].operation_symbol == "<") || (intermediate_code_list[i][j].operation_symbol == ">"))
			{//(w B C A)
				if (intermediate_code_list[i][j].operation_symbol == "==")
				{
					temp_code = ("\tJNE FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				else if (intermediate_code_list[i][j].operation_symbol == "!=")
				{
					temp_code = ("\tJE FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				else if (intermediate_code_list[i][j].operation_symbol == "<=")
				{
					temp_code = ("\tJA FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				else if (intermediate_code_list[i][j].operation_symbol == ">=")
				{
					temp_code = ("\tJB FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				else if (intermediate_code_list[i][j].operation_symbol == "<")
				{
					temp_code = ("\tJAE FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				else if (intermediate_code_list[i][j].operation_symbol == ">")
				{
					temp_code = ("\tJBE FUN" + to_string(part_number));
					temp_position.push("FUN" + to_string(part_number));
					part_number++;
				}
				if (symbol_to_reg.count(intermediate_code_list[i][j].first_target.operation_target_value))
				{//��ǰ�мĴ������˵�һ��ֵ
					object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
				}
				else if (symbol_to_reg.count(intermediate_code_list[i][j].second_target.operation_target_value) && (intermediate_code_list[i][j].operation_symbol == "=="))
				{//��ǰ�мĴ������˵ڶ�������ֵ�����������Ϊ==
					object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].second_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
				}
				else
				{//��Ҫһ���¼Ĵ���
					if (get_symbol_size[function_number][intermediate_code_list[i][j].first_target.operation_target_value] == 8)
					{//double����Ҫ16λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_16.size(); k++)
						{
							if (!reg_to_symbol.count(reg_16[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_16[k], intermediate_code_list[i][j].first_target.operation_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, reg_16[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								//CMP reg_A,B
								flag = 1;
								break;
							}
						}
						if (flag)
							continue;
					}
					else
					{//��������ֻ��Ҫ8λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_8.size(); k++)
						{
							if (!reg_to_symbol.count(reg_8[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_8[k], intermediate_code_list[i][j].first_target.operation_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, reg_8[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								//CMP reg_A,B
								flag = 1;
								break;
							}
						}
						if (flag)
							continue;
					}
					if (get_symbol_size[function_number][intermediate_code_list[i][j].first_target.operation_target_value] == 8)
					{//û�п��мĴ��������豣��Ĵ�����ֵ
						if ((get_symbol_number(reg_to_symbol["AX"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AX"], i)].offset) + "]," + reg_to_symbol["AX"]);
							symbol_to_reg.erase(reg_to_symbol["AX"]);
							reg_to_symbol["AX"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AX", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AX"));
						object_code_list[i].push_back("\tPUSH AX");
						object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
						object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						object_code_list[i].push_back("\tPOP AX");
						continue;
					}
					else
					{
						if ((get_symbol_number(reg_to_symbol["AL"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AL"], i)].offset) + "]," + reg_to_symbol["AL"]);
							symbol_to_reg.erase(reg_to_symbol["AL"]);
							reg_to_symbol["AL"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AL", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AL"));
						object_code_list[i].push_back("\tPUSH AL");
						object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
						object_code_list[i].push_back("\tCMP " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						object_code_list[i].push_back("\tPOP AL");
						continue;
					}
				}
			}

			/*��������*/
			else if ((intermediate_code_list[i][j].operation_symbol == "+") || (intermediate_code_list[i][j].operation_symbol == "-") || (intermediate_code_list[i][j].operation_symbol == "*") || (intermediate_code_list[i][j].operation_symbol == "/"))
			{//(w B C A)
				if (symbol_to_reg.count(intermediate_code_list[i][j].first_target.operation_target_value))
				{//��ǰ�мĴ������˵�һ��ֵ
					if (intermediate_code_list[i][j].operation_symbol == "+")
						object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "-")
						object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "*")
						object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else
						object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
				}
				else if (symbol_to_reg.count(intermediate_code_list[i][j].second_target.operation_target_value) && ((intermediate_code_list[i][j].operation_symbol == "+") || (intermediate_code_list[i][j].operation_symbol == "*")))
				{//��ǰ�мĴ������˵ڶ�������ֵ�����������Ϊ+����*
					if (intermediate_code_list[i][j].operation_symbol == "+")
						object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].second_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "*")
						object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].second_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
				}
				else
				{//��Ҫһ���¼Ĵ���
					if (get_symbol_size[function_number][intermediate_code_list[i][j].first_target.operation_target_value] == 8)
					{//double����Ҫ16λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_16.size(); k++)
						{
							if (!reg_to_symbol.count(reg_16[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_16[k], intermediate_code_list[i][j].first_target.operation_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, reg_16[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								if (intermediate_code_list[i][j].operation_symbol == "+")
									object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else if (intermediate_code_list[i][j].operation_symbol == "-")
									object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else if (intermediate_code_list[i][j].operation_symbol == "*")
									object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else
									object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								flag = 1;
								break;
							}
						}
						if (flag)
							continue;
					}
					else
					{//��������ֻ��Ҫ8λ�Ĵ���
						int k, flag = 0;
						for (k = 0; k < reg_8.size(); k++)
						{
							if (!reg_to_symbol.count(reg_8[k]))
							{
								reg_to_symbol.insert(pair<string, string>(reg_8[k], intermediate_code_list[i][j].first_target.operation_target_value));
								symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, reg_8[k]));
								object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
								if (intermediate_code_list[i][j].operation_symbol == "+")
									object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else if (intermediate_code_list[i][j].operation_symbol == "-")
									object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else if (intermediate_code_list[i][j].operation_symbol == "*")
									object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								else
									object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
								flag = 1;
								break;
							}
						}
						if (flag)
							continue;
					}
					if (get_symbol_size[function_number][intermediate_code_list[i][j].first_target.operation_target_value] == 8)
					{//û�п��мĴ��������豣��Ĵ�����ֵ
						if ((get_symbol_number(reg_to_symbol["AX"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AX"], i)].offset) + "]," + reg_to_symbol["AX"]);
							symbol_to_reg.erase(reg_to_symbol["AX"]);
							reg_to_symbol["AX"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AX", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AX"));
						object_code_list[i].push_back("\tPUSH AX");
						object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
						if (intermediate_code_list[i][j].operation_symbol == "+")
							object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else if (intermediate_code_list[i][j].operation_symbol == "-")
							object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else if (intermediate_code_list[i][j].operation_symbol == "*")
							object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else
							object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						object_code_list[i].push_back("\tPOP AX");
						continue;
					}
					else
					{
						if ((get_symbol_number(reg_to_symbol["AL"], i)) != 0)
						{//��ǰAX�Ĵ���������Ҫ����
							object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AL"], i)].offset) + "]," + reg_to_symbol["AL"]);
							symbol_to_reg.erase(reg_to_symbol["AL"]);
							reg_to_symbol["AL"] = intermediate_code_list[i][j].first_target.operation_target_value;
						}
						//reg_to_symbol.insert(pair<string, string>("AL", intermediate_code_list[i][j].first_target.operation_target_value));
						symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AL"));
						object_code_list[i].push_back("\tPUSH AL");
						if (intermediate_code_list[i][j].operation_symbol == "+")
							object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else if (intermediate_code_list[i][j].operation_symbol == "-")
							object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else if (intermediate_code_list[i][j].operation_symbol == "*")
							object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						else
							object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
						object_code_list[i].push_back("\tPOP AL");
						continue;
					}
				}
			}

			/*�����Լ�*/
			else if ((intermediate_code_list[i][j].operation_symbol == "behind++") || (intermediate_code_list[i][j].operation_symbol == "front++"))
			{//behind++ _ _ _
				object_code_list[i].push_back("\tPUSH AX");
				object_code_list[i].push_back("\tMOV AX,DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(intermediate_code_list[i][j].result_target.result_target_value, i)].offset) + "D]");
				object_code_list[i].push_back("\tINC AX");
				object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(intermediate_code_list[i][j].result_target.result_target_value, i)].offset) + "D],AX");
				object_code_list[i].push_back("\tPOP AX");
			}
			else if ((intermediate_code_list[i][j].operation_symbol == "behind--") || (intermediate_code_list[i][j].operation_symbol == "front--"))
			{
				object_code_list[i].push_back("\tPUSH AX");
				object_code_list[i].push_back("\tMOV AX,DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(intermediate_code_list[i][j].result_target.result_target_value, i)].offset) + "D]");
				object_code_list[i].push_back("\tDEC AX");
				object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(intermediate_code_list[i][j].result_target.result_target_value, i)].offset) + "D],AX");
				object_code_list[i].push_back("\tPOP AX");
			}

			/*����ȡֵ*/
			else if (intermediate_code_list[i][j].operation_symbol == "array")
			{//array arr 9 c
				if (get_symbol_size[function_number][intermediate_code_list[i][j].first_target.operation_target_value] == 8)
				{//û�п��мĴ��������豣��Ĵ�����ֵ
					if ((get_symbol_number(reg_to_symbol["AX"], i)) != 0)
					{//��ǰAX�Ĵ���������Ҫ����
						object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AX"], i)].offset) + "]," + reg_to_symbol["AX"]);
						symbol_to_reg.erase(reg_to_symbol["AX"]);
						reg_to_symbol["AX"] = intermediate_code_list[i][j].first_target.operation_target_value;
					}
					//reg_to_symbol.insert(pair<string, string>("AX", intermediate_code_list[i][j].first_target.operation_target_value));
					symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AX"));
					object_code_list[i].push_back("\tPUSH AX");
					object_code_list[i].push_back("\tMOV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].first_target.operation_target_value);
					if (intermediate_code_list[i][j].operation_symbol == "+")
						object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "-")
						object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "*")
						object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else
						object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					object_code_list[i].push_back("\tPOP AX");
					continue;
				}
				else
				{
					if ((get_symbol_number(reg_to_symbol["AL"], i)) != 0)
					{//��ǰAX�Ĵ���������Ҫ����
						object_code_list[i].push_back("\tMOV DS:[" + to_string(symbol_table_list[i].symbol_property_table[get_symbol_number(reg_to_symbol["AL"], i)].offset) + "]," + reg_to_symbol["AL"]);
						symbol_to_reg.erase(reg_to_symbol["AL"]);
						reg_to_symbol["AL"] = intermediate_code_list[i][j].first_target.operation_target_value;
					}
					//reg_to_symbol.insert(pair<string, string>("AL", intermediate_code_list[i][j].first_target.operation_target_value));
					symbol_to_reg.insert(pair<string, string>(intermediate_code_list[i][j].first_target.operation_target_value, "AL"));
					object_code_list[i].push_back("\tPUSH AL");
					if (intermediate_code_list[i][j].operation_symbol == "+")
						object_code_list[i].push_back("\tADD " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "-")
						object_code_list[i].push_back("\tSUB " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else if (intermediate_code_list[i][j].operation_symbol == "*")
						object_code_list[i].push_back("\tMUL " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					else
						object_code_list[i].push_back("\tDIV " + symbol_to_reg[intermediate_code_list[i][j].first_target.operation_target_value] + "," + intermediate_code_list[i][j].second_target.operation_target_value);
					object_code_list[i].push_back("\tPOP AL");
					continue;
				}
			}
		}
		object_code_list[i].push_back("\tMOV AX,4C00H");
		object_code_list[i].push_back("\tINT 21H");//�ж�
		object_code_list[i].push_back("CODE\tENDS");//����ν���
		object_code_list[i].push_back("END\tSTART");//�������
	}
	for (int i = 0; i < object_code_list.size(); i++)
	{
		for (int j = 0; j < object_code_list[i].size(); j++)
		{
			cout << object_code_list[i][j] << endl;
		}
	}
}

void Code_Generation::write_in_file()
{
	fstream file;
	file.open("OutputFile.txt");
	for (int i = 0; i < object_code_list.size(); i++)
	{
		for (int j = 0; j < object_code_list[i].size(); j++)
		{
			file << object_code_list[i][j];
			file << endl;
		}
	}
}