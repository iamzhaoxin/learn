
#include "Code_Process.h"

Code_Process::Code_Process()
{

}

Code_Process::~Code_Process()
{

}

void Code_Process::get_symbol_table_list(vector<symbol_table> s)
{
	this->symbol_table_list = s;
}

void Code_Process::get_four_item_production(vector<vector<four_item_production>> f)
{
	this->four_item_production_list = f;
}

void Code_Process::get_const_symbol(vector<string> c)
{
	this->const_symbol = c;
}

void Code_Process::print_old_four_item_production()//�����Ԫʽ
{
	cout << "δ�Ż�����Ԫʽ��:" << endl;
	for (unsigned int i = 0; i < four_item_production_list.size(); i++)
	{
		cout << "����" << i << ":" << endl;
		for (unsigned int j = 0; j < four_item_production_list[i].size(); j++)
		{
			cout << j << "��ʽ:" << four_item_production_list[i][j].basic_block_flag << "  " << four_item_production_list[i][j].operation_symbol << "  ";
			if (four_item_production_list[i][j].first_target.operation_target_value != "_")
				cout << four_item_production_list[i][j].first_target.operation_target_value << "(" << four_item_production_list[i][j].first_target.active_information << ")\t";
			else
				cout << four_item_production_list[i][j].first_target.operation_target_value << "\t";
			if (four_item_production_list[i][j].second_target.operation_target_value != "_")
				cout << four_item_production_list[i][j].second_target.operation_target_value << "(" << four_item_production_list[i][j].second_target.active_information << ")\t";
			else
				cout << four_item_production_list[i][j].second_target.operation_target_value << "\t";
			if (four_item_production_list[i][j].result_target.result_target_value != "_")
				cout << four_item_production_list[i][j].result_target.result_target_value << "(" << four_item_production_list[i][j].result_target.active_information << ")" << endl;
			else
				cout << four_item_production_list[i][j].result_target.result_target_value << endl;
		}
		cout << endl;
	}
}

void Code_Process::print_new_four_item_production()//�����Ԫʽ
{
	cout << "�Ż������Ԫʽ��:" << endl;
	for (unsigned int i = 0; i < new_four_item_production_list.size(); i++)
	{
		cout << "����" << i << ":" << endl;
		for (unsigned int j = 0; j < new_four_item_production_list[i].size(); j++)
		{
			cout << j << "��ʽ:" << new_four_item_production_list[i][j].basic_block_flag << "  " << new_four_item_production_list[i][j].operation_symbol << "  ";
			if (new_four_item_production_list[i][j].first_target.operation_target_value != "_")
				cout << new_four_item_production_list[i][j].first_target.operation_target_value << "(" << new_four_item_production_list[i][j].first_target.active_information << ")\t";
			else
				cout << new_four_item_production_list[i][j].first_target.operation_target_value << "\t";
			if (four_item_production_list[i][j].second_target.operation_target_value != "_")
				cout << new_four_item_production_list[i][j].second_target.operation_target_value << "(" << new_four_item_production_list[i][j].second_target.active_information << ")\t";
			else
				cout << new_four_item_production_list[i][j].second_target.operation_target_value << "\t";
			if (four_item_production_list[i][j].result_target.result_target_value != "_")
				cout << new_four_item_production_list[i][j].result_target.result_target_value << "(" << new_four_item_production_list[i][j].result_target.active_information << ")" << endl;
			else
				cout << new_four_item_production_list[i][j].result_target.result_target_value << endl;
		}
		cout << endl;
	}
}
void Code_Process::print_DAG_information()
{
	for (int k = 0; k < out_DAG.size(); k++) {
		cout << k << "��ͼ��" << endl;
		for (int i = 0; i < out_DAG[k].size(); i++)
		{
			cout << out_DAG[k][i].node_number << "�ŵ�:����ǣ�" << out_DAG[k][i].main_mark << "�����ӱ�ǣ�";
			for (int j = 0; j < out_DAG[k][i].other_mark.size(); j++)
			{
				cout << out_DAG[k][i].other_mark[j] << " ";
			}
			cout << "\n�������" << out_DAG[k][i].operate_symbol;
			cout << "\nǰ����";
			for (int j = 0; j < out_DAG[k][i].front_node.size(); j++)
			{
				cout << out_DAG[k][i].front_node[j] << " ";
			}
			cout << "\n��̣�";
			for (int j = 0; j < out_DAG[k][i].rear_node.size(); j++)
			{
				cout << out_DAG[k][i].rear_node[j] << " ";
			}
			cout << endl;
		}
		cout << endl;
	}
}

void Code_Process::divide_basic_block(vector<vector<four_item_production>>* four_item_production_list)//���ֻ�����
{
	for (int j = 0; j < (*four_item_production_list).size(); j++)
	{
		(*four_item_production_list)[j][0].basic_block_flag = "��ڿ�";
		(*four_item_production_list)[j][(*four_item_production_list)[j].size() - 1].basic_block_flag = "���ڿ�";
		for (__int64 i = 0; i < (*four_item_production_list)[j].size(); i++)
		{
			if (((*four_item_production_list)[j][i].operation_symbol == "ifEndJudge") || ((*four_item_production_list)[j][i].operation_symbol == "ifJudge") || ((*four_item_production_list)[j][i].operation_symbol == "endIf") || ((*four_item_production_list)[j][i].operation_symbol == "whileDoIf") || ((*four_item_production_list)[j][i].operation_symbol == "forDoIf") || ((*four_item_production_list)[j][i].operation_symbol == "case") || ((*four_item_production_list)[j][i].operation_symbol == "switch"))
			{//��ת������һ�������
				(*four_item_production_list)[j][i + 1].basic_block_flag = "��ڿ�";
			}
			if (((*four_item_production_list)[j][i].operation_symbol == "ifToEnd") || ((*four_item_production_list)[j][i].operation_symbol == "endWhile") || ((*four_item_production_list)[j][i].operation_symbol == "endFor") || ((*four_item_production_list)[j][i].operation_symbol == "endSwitch") || ((*four_item_production_list)[j][i].operation_symbol == "caseEnd"))
			{//��ת�����ת����λ�õ������
				(*four_item_production_list)[j][i + 1].basic_block_flag = "��ڿ�";
			}
			if (((*four_item_production_list)[j][i].operation_symbol == "else") || ((*four_item_production_list)[j][i].operation_symbol == "forBeginJudge") || ((*four_item_production_list)[j][i].operation_symbol == "afterForDo") || ((*four_item_production_list)[j][i].operation_symbol == "forDo") || ((*four_item_production_list)[j][i].operation_symbol == "beginWhile") || ((*four_item_production_list)[j][i].operation_symbol == "default") || ((*four_item_production_list)[j][i].operation_symbol == "forBegin"))
			{//��ת�����ת����λ�������
				(*four_item_production_list)[j][i].basic_block_flag = "��ڿ�";
			}
		}
		for (__int64 i = (*four_item_production_list)[j].size() - 1; i > 0; i--)
		{
			if ((*four_item_production_list)[j][i].basic_block_flag == "��ڿ�")
			{//��ڿ��ǰһ��λ���ǳ��ڿ飬��һ����ڿ����
				(*four_item_production_list)[j][i - 1].basic_block_flag = "���ڿ�";
			}
		}
	}
}

void Code_Process::initialize_active_information(vector<vector<four_item_production>>* four_item_production_list)//��Ծ��Ϣ��ʼ��ǰ��ʼ��������
{
	for (unsigned int k = 0, j = -1; k < (*four_item_production_list).size(); k++)
	{
		for (__int64 i = 0; i < (*four_item_production_list)[k].size(); i++)
		{
			if ((*four_item_production_list)[k][i].basic_block_flag == "��ڿ�")
			{
				j++;
				symbol_active_list.resize(symbol_active_list.size() + 1);
			}
			if (!symbol_active_list[j].count((*four_item_production_list)[k][i].first_target.operation_target_value))
			{//������
				if (block_part_variable_flag((*four_item_production_list)[k][i].first_target.operation_target_value))
				{//���ھֲ�����
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].first_target.operation_target_value, false));
				}
				else
				{
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].first_target.operation_target_value, true));
				}

			}
			if (!symbol_active_list[j].count((*four_item_production_list)[k][i].second_target.operation_target_value))
			{//������
				if (block_part_variable_flag((*four_item_production_list)[k][i].second_target.operation_target_value))
				{//���ھֲ�����
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].second_target.operation_target_value, false));
				}
				else
				{
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].second_target.operation_target_value, true));
				}

			}
			if (!symbol_active_list[j].count((*four_item_production_list)[k][i].result_target.result_target_value))
			{//������
				if (block_part_variable_flag((*four_item_production_list)[k][i].result_target.result_target_value))
				{//���ھֲ�����
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].result_target.result_target_value, false));
				}
				else
				{
					symbol_active_list[j].insert(pair<string, bool>((*four_item_production_list)[k][i].result_target.result_target_value, true));
				}
			}
		}
	}
}

void Code_Process::change_active_information(vector<vector<four_item_production>>* four_item_production_list)//Ϊ������ӳ�ʼ����Ծ��Ϣ
{
	for (int j = (*four_item_production_list).size() - 1, k = symbol_active_list.size(); j > 0; j--) {
		for (int i = (*four_item_production_list)[j].size() - 1; i > 0; i--)
		{//�����һ�������鿪ʼ����
			if ((*four_item_production_list)[j][i].basic_block_flag == "���ڿ�")
			{//�������ڿ��־
				k--;
			}
			if ((*four_item_production_list)[j][i].result_target.result_target_value != "_")
			{
				(*four_item_production_list)[j][i].result_target.active_information = symbol_active_list[k].find((*four_item_production_list)[j][i].result_target.result_target_value)->second;
				symbol_active_list[k][(*four_item_production_list)[j][i].result_target.result_target_value] = false;
			}
			if ((*four_item_production_list)[j][i].first_target.operation_target_value != "_")
			{
				(*four_item_production_list)[j][i].first_target.active_information = symbol_active_list[k].find((*four_item_production_list)[j][i].first_target.operation_target_value)->second;
				symbol_active_list[k][(*four_item_production_list)[j][i].first_target.operation_target_value] = true;
			}
			if ((*four_item_production_list)[j][i].second_target.operation_target_value != "_")
			{
				(*four_item_production_list)[j][i].second_target.active_information = symbol_active_list[k].find((*four_item_production_list)[j][i].second_target.operation_target_value)->second;
				symbol_active_list[k][(*four_item_production_list)[j][i].second_target.operation_target_value] = true;
			}
		}
	}
}

void Code_Process::code_optomization()//�����Ż�
{//�������ǵ�˳�򣺳���>��ʱ����>����ʱ����
	this->divide_basic_block(&four_item_production_list);//���ֻ�����
	this->initialize_active_information(&four_item_production_list);//�������ڱ�����Ծ��Ϣ�ĳ�ʼ��Ԥ������
	this->change_active_information(&four_item_production_list);//�������ڱ�����Ծ��Ϣ�����
	int jump_flag = 0;//����break����continue������
	four_item_production jump_action;//break����continue����Ԫʽ
	for (int j = 0; j < four_item_production_list.size(); j++)
	{//����ÿһ����������Ԫʽ��
		function_number++;
		get_symbol_size.resize(get_symbol_size.size() + 1);
		for (int i = 0; i < four_item_production_list[j].size(); i++)
		{//����һ��������Ԫʽ���е�ÿһ��
			if (four_item_production_list[j][i].basic_block_flag == "��ڿ�")
			{
				temp_f.push_back(four_item_production_list[j][i]);//ֱ�Ӽ�����ʱ��Ԫʽ���鿴��һ��
				continue;
			}
			else if ((four_item_production_list[j][i].operation_symbol == "break") || (four_item_production_list[j][i].operation_symbol == "continue"))
			{
				jump_flag = 1;
				jump_action = four_item_production_list[j][i];
			}
			else if (four_item_production_list[j][i].basic_block_flag == "���ڿ�")
			{
				handle_DAG();//����DAGͼ�������Ż������Ԫʽ������temp_f��
				out_DAG.push_back(DAG_map);
				DAG_map.clear();//���DAG
				DAG_symbol_to_node.clear();//��ճ�������ʶ�������Ӧͼ
				if (jump_flag == 1) {
					temp_f.push_back(jump_action);
					jump_flag = 0;
				}
				temp_f.push_back(four_item_production_list[j][i]);
				continue;
			}
			else if ((four_item_production_list[j][i].operation_symbol == "break") || (four_item_production_list[j][i].operation_symbol == "continue"))
			{
				temp_f.push_back(four_item_production_list[j][i]);
				continue;
			}
			else if (four_item_production_list[j][i].operation_symbol == "=")
			{//��ֵ���ʽ:A=B��BΪ���������
				int B_node = get_A_B_node_number(four_item_production_list[j][i].first_target.operation_target_value);//����B�Ľ���Ƿ����
				if (DAG_symbol_to_node.count(four_item_production_list[j][i].result_target.result_target_value))
				{//��ǰ���н��ĸ��ӱ������A
					vector<string>* s = &DAG_map[DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value]].other_mark;
					s->erase(remove(s->begin(), s->end(), four_item_production_list[j][i].result_target.result_target_value), s->end());
					//�Ƴ��丽�ӱ���е�A
					DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value] = B_node;
					//A���������B�������
				}
				else
				{//��ǰû�а���A�Ľ��
					DAG_symbol_to_node.insert(pair<string, int>(four_item_production_list[j][i].result_target.result_target_value, B_node));
					//����A������ţ�ΪB�������
				}
				DAG_map[B_node].other_mark.push_back(four_item_production_list[j][i].result_target.result_target_value);
				//A����B���ĸ��ӱ��
				handle_main_other_order(B_node);
				//����һ�����������Ǻ͸��ӱ�ǵĹ�ϵ
				continue;
			}
			else if (const_number_flag(four_item_production_list[j][i].first_target.operation_target_value) && const_number_flag(four_item_production_list[j][i].second_target.operation_target_value))
			{//�������ʽ��A=C1wC2��C1wC2Ϊ����P
				int P_node = get_A_C1wC2_node_number(four_item_production_list[j][i].first_target.operation_target_value, four_item_production_list[j][i].second_target.operation_target_value, four_item_production_list[j][i].operation_symbol);
				if (DAG_symbol_to_node.count(four_item_production_list[j][i].result_target.result_target_value))
				{//��ǰ���н��ĸ��ӱ������A
					vector<string>* s = &DAG_map[DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value]].other_mark;
					s->erase(remove(s->begin(), s->end(), four_item_production_list[j][i].result_target.result_target_value), s->end());
					//�Ƴ��丽�ӱ���е�A
					DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value] = P_node;
					//A���������P�������
				}
				else
				{//��ǰû�а���A�Ľ��
					DAG_symbol_to_node.insert(pair<string, int>(four_item_production_list[j][i].result_target.result_target_value, P_node));
					//����A������ţ�ΪP�������
				}
				DAG_map[P_node].other_mark.push_back(four_item_production_list[j][i].result_target.result_target_value);
				//A����P���ĸ��ӱ��
				handle_main_other_order(P_node);
				//����һ�����������Ǻ͸��ӱ�ǵĹ�ϵ
				continue;
			}
			else if (four_item_production_list[j][i].second_target.operation_target_value == "_")
			{//������ʽ��A=wB��B��Ϊ����
				int wB_node = get_A_wB_node_number(four_item_production_list[j][i].first_target.operation_target_value, four_item_production_list[j][i].operation_symbol);
				if (DAG_symbol_to_node.count(four_item_production_list[j][i].result_target.result_target_value))
				{//��ǰ���н��ĸ��ӱ������A
					vector<string>* s = &DAG_map[DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value]].other_mark;
					s->erase(remove(s->begin(), s->end(), four_item_production_list[j][i].result_target.result_target_value), s->end());
					//�Ƴ��丽�ӱ���е�A
					DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value] = wB_node;
					//A���������wB�������
				}
				else
				{//��ǰû�а���A�Ľ��
					DAG_symbol_to_node.insert(pair<string, int>(four_item_production_list[j][i].result_target.result_target_value, wB_node));
					//A���������wB�������
				}
				DAG_map[wB_node].other_mark.push_back(four_item_production_list[j][i].result_target.result_target_value);
				//A����wB���ĸ��ӱ��
				handle_main_other_order(wB_node);
				//����һ�����������Ǻ͸��ӱ�ǵĹ�ϵ
				continue;
			}
			else
			{//������ʽ��A=BwC��B��C��Ϊ����
				int BwC_node = get_A_BwC_node_number(four_item_production_list[j][i].first_target.operation_target_value, four_item_production_list[j][i].second_target.operation_target_value, four_item_production_list[j][i].operation_symbol);
				if (DAG_symbol_to_node.count(four_item_production_list[j][i].result_target.result_target_value))
				{//��ǰ���н��ĸ��ӱ������A
					vector<string>* s = &DAG_map[DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value]].other_mark;
					s->erase(remove(s->begin(), s->end(), four_item_production_list[j][i].result_target.result_target_value), s->end());
					//�Ƴ��丽�ӱ���е�A
					DAG_symbol_to_node[four_item_production_list[j][i].result_target.result_target_value] = BwC_node;
					//A���������BwC�������
				}
				else
				{//��ǰû�а���A�Ľ��
					DAG_symbol_to_node.insert(pair<string, int>(four_item_production_list[j][i].result_target.result_target_value, BwC_node));
					//A���������BwC�������
				}
				DAG_map[BwC_node].other_mark.push_back(four_item_production_list[j][i].result_target.result_target_value);
				//A����BwC���ĸ��ӱ��
				handle_main_other_order(BwC_node);
				//����һ�����������Ǻ͸��ӱ�ǵĹ�ϵ
				continue;
			}
		}
		new_four_item_production_list.push_back(temp_f);
		temp_f.clear();
	}
	this->divide_basic_block(&new_four_item_production_list);//�Ż�����Ԫʽ���ֻ�����
	this->initialize_active_information(&new_four_item_production_list);//�Ż�����Ԫʽ�������ڱ�����Ծ��Ϣ�ĳ�ʼ��Ԥ������
	this->change_active_information(&new_four_item_production_list);//�Ż�����Ԫʽ�������ڱ�����Ծ��Ϣ�����
}

int Code_Process::block_part_variable_flag(string s)//�ж�һ�������Ƿ��ǿ��ھֲ�����
{
	if ((isdigit(s[0])) && (s[s.size() - 1] == 't'))
	{
		return 1;
	}
	else
		return 0;
}

int Code_Process::const_symbol_flag(string s)//�ж�һ���ַ��Ƿ��Ǳ�ʶ��
{
	for (int i = 1; i <= const_symbol.size(); i++)
	{
		if (const_symbol[i] == s)
		{
			return 1;
		}
	}
	return 0;
}

int Code_Process::const_number_flag(string s)//�ж�һ���ַ��Ƿ��ǳ���
{
	if (isdigit(s[0]) && isdigit(s[s.size() - 1]))
		return 1;
	else
		return 0;
}

int Code_Process::get_A_B_node_number(string B)//����B������ţ����������������
{
	if (DAG_symbol_to_node.count(B))
	{//���B������ڵĽ����ڣ�ֱ�ӷ��ظý���ֵ
		return DAG_symbol_to_node[B];
	}
	else
	{
		DAG_property temp_node;//����DAGͼʱ����ʱ���
		temp_node.node_number = DAG_map.size();//�½�����Ϊ��ǰDAG�ĳ���
		temp_node.main_mark = B;
		temp_node.other_mark.clear();
		if ((B[0] == '\'') || (B[0] == '\"'))
			temp_node.symbol_size = 4;
		else if (B.size() == 1 && isdigit(B[0]))
			temp_node.symbol_size = 4;
		else if (B[1] == '.')
			temp_node.symbol_size = 8;
		else if (isdigit(B[0]))
			temp_node.symbol_size = 4;
		else if (symbol_table_list[function_number].symbol_property_table[get_symbol_number(B, function_number)].type->type_code == "double")
			temp_node.symbol_size = 8;
		else
			temp_node.symbol_size = 4;
		DAG_map.push_back(temp_node);//DAGͼ������
		DAG_symbol_to_node.insert(pair<string, int>(B, temp_node.node_number));
		//B����������½��������
		return DAG_symbol_to_node[B];
	}
}

int Code_Process::get_A_C1wC2_node_number(string c1, string c2, string w)//����P=C1wC2������ţ����������������
{
	double result = 0;
	if (!strcmp(w.c_str(), "+"))
		result = atof(c1.c_str()) + atof(c2.c_str());
	else if (!strcmp(w.c_str(), "-"))
		result = atof(c1.c_str()) - atof(c2.c_str());
	else if (!strcmp(w.c_str(), "*"))
		result = atof(c1.c_str()) * atof(c2.c_str());
	else if (!strcmp(w.c_str(), "/"))
		result = atof(c1.c_str()) / atof(c2.c_str());
	if (DAG_symbol_to_node.count(to_string(result)))
	{//�����ֵ���ʽ�Ľ��P����ĳһ����㣬���ؽ���
		return DAG_symbol_to_node[to_string(result)];
	}
	else
	{//�����½�P���
		DAG_property temp_node;//����DAGͼʱ����ʱ���
		temp_node.node_number = DAG_map.size();//�½�����Ϊ��ǰDAG�ĳ���
		temp_node.main_mark = to_string(result);//�����ΪP
		temp_node.other_mark.clear();
		if ((to_string(result)[0] == '\'') || (to_string(result)[0] == '\"'))
			temp_node.symbol_size = 4;
		else if (to_string(result).size() == 1 && isdigit(to_string(result)[0]))
			temp_node.symbol_size = 4;
		else if (to_string(result)[1] == '.')
			temp_node.symbol_size = 8;
		else if (isdigit(to_string(result)[0]))
			temp_node.symbol_size = 4;
		else if ((symbol_table_list[function_number].symbol_property_table[get_symbol_number(c1, function_number)].type->type_code != "double") && (symbol_table_list[function_number].symbol_property_table[get_symbol_number(c2, function_number)].type->type_code != "double"))
			temp_node.symbol_size = 4;
		else
			temp_node.symbol_size = 8;
		DAG_map.push_back(temp_node);//DAGͼ������
		DAG_symbol_to_node.insert(pair<string, int>(to_string(result), temp_node.node_number));
		//P����������½��������
		return DAG_symbol_to_node[to_string(result)];
	}
}

int Code_Process::get_A_wB_node_number(string B, string w)//����wB����е���ţ����������������
{
	if (DAG_symbol_to_node.count(B))
	{//�а���B�Ľ��
		for (int i = 0; i < DAG_map[DAG_symbol_to_node[B]].front_node.size(); i++)
		{//�鿴B������ǰ�����
			if (DAG_map[DAG_map[DAG_symbol_to_node[B]].front_node[i]].operate_symbol == w)
			{//����һB����ǰ����㣬�������w����wB�е�w
				return DAG_map[DAG_map[DAG_symbol_to_node[B]].front_node[i]].node_number;
			}
		}
		//B��ǰ�������û����һ���������Ϊw�����½�wB���
		DAG_property temp_node;
		temp_node.node_number = DAG_map.size();//�½�����Ϊ��ǰDAG�ĳ���
		temp_node.operate_symbol = w;//�½�������Ϊw
		temp_node.rear_node.push_back(DAG_symbol_to_node[B]);//B�������½��ĺ�̽��
		temp_node.symbol_size = DAG_map[DAG_symbol_to_node[B]].symbol_size;
		DAG_map.push_back(temp_node);
		return temp_node.node_number;
	}
	else
	{//û�а���B�Ľ�㣬Ҳ��Ȼû��wB�Ľ��
		DAG_property temp_node;
		temp_node.node_number = DAG_map.size();//�½�����Ϊ��ǰDAG�ĳ���
		temp_node.main_mark = B;//�½�������ΪB
		temp_node.front_node.push_back(DAG_map.size() + 1);//ǰ��Ϊ��һ�����õĲ�����Ϊw�Ľ��
		if ((B[0] == '\'') || (B[0] == '\"'))
			temp_node.symbol_size = 4;
		else if (B.size() == 1 && isdigit(B[0]))
			temp_node.symbol_size = 4;
		else if (B[1] == '.')
			temp_node.symbol_size = 8;
		else if (isdigit(B[0]))
			temp_node.symbol_size = 4;
		else if (symbol_table_list[function_number].symbol_property_table[get_symbol_number(B, function_number)].type->type_code == "double")
			temp_node.symbol_size = 8;
		else
			temp_node.symbol_size = 4;
		DAG_symbol_to_node.insert(pair<string, int>(B, temp_node.node_number));//B����Ķ�Ӧ��ϵ�������ͼ
		DAG_map.push_back(temp_node);
		temp_node.node_number = DAG_map.size();//�½�����Ϊ��ǰDAG�ĳ���
		temp_node.operate_symbol = w;//�½�������Ϊw
		temp_node.main_mark.clear();//��������
		temp_node.front_node.clear();//ǰ�����
		temp_node.rear_node.push_back(DAG_symbol_to_node[B]);//B�������½��ĺ�̽��
		temp_node.symbol_size = DAG_map[DAG_symbol_to_node[B]].symbol_size;
		DAG_map.push_back(temp_node);
		return temp_node.node_number;
	}
	return 0;
}

int Code_Process::get_A_BwC_node_number(string B, string C, string w)//����BwC����е���ţ����������������
{
	if (DAG_symbol_to_node.count(B) && DAG_symbol_to_node.count(C))
	{//B��C���ڲ���ͼ��
		for (int i = 0; i < DAG_map[DAG_symbol_to_node[B]].front_node.size(); i++)
		{//�鿴B������ǰ�����
			if (DAG_map[DAG_map[DAG_symbol_to_node[B]].front_node[i]].operate_symbol == w)
			{//����һB����ǰ����㣬�����������BwC�е�w
				int temp_result = DAG_map[DAG_symbol_to_node[B]].front_node[i];//������ʱ������
				for (int j = 0; j < DAG_map[DAG_symbol_to_node[C]].front_node.size(); j++)
				{//�鿴C������ǰ�����
					if (DAG_map[DAG_map[DAG_symbol_to_node[C]].front_node[j]].operate_symbol == w)
					{//����һC����ǰ����㣬�������w����BwC�е�w
						if (DAG_map[DAG_symbol_to_node[C]].front_node[j] == temp_result)
						{//B��C��ĳһ�������Ϊw��ǰ����ͬһ����㣬��BwC������
							return temp_result;
						}
					}
				}
				//���Cû��ǰ�����������Ϊw�����������Ϊw��Cǰ�������B����ͬһ��㣬�����½�BwC��
				DAG_property temp_node;
				temp_node.node_number = DAG_map.size();
				//B C�����½����
				temp_node.rear_node.push_back(DAG_symbol_to_node[B]);
				temp_node.rear_node.push_back(DAG_symbol_to_node[C]);
				temp_node.operate_symbol = w;//�½��������Ϊw
				if ((DAG_map[DAG_symbol_to_node[B]].symbol_size != 8) && (DAG_map[DAG_symbol_to_node[C]].symbol_size != 8))
					temp_node.symbol_size = 4;
				else
					temp_node.symbol_size = 8;
				DAG_map[DAG_symbol_to_node[C]].front_node.push_back(temp_node.node_number);//w����C����ǰ��
				DAG_map.push_back(temp_node);
				return temp_node.node_number;
			}
		}
		//���Bû��ǰ�����������Ϊw�������½�BwC��
		DAG_property temp_node;
		temp_node.node_number = DAG_map.size();
		//B C�����½����
		temp_node.rear_node.push_back(DAG_symbol_to_node[B]);
		temp_node.rear_node.push_back(DAG_symbol_to_node[C]);
		temp_node.operate_symbol = w;//�½��������Ϊw
		if ((DAG_map[DAG_symbol_to_node[B]].symbol_size != 8) && (DAG_map[DAG_symbol_to_node[C]].symbol_size != 8))
			temp_node.symbol_size = 4;
		else
			temp_node.symbol_size = 8;
		DAG_map[DAG_symbol_to_node[B]].front_node.push_back(temp_node.node_number);//w����B����ǰ��
		DAG_map.push_back(temp_node);
		for (int j = 0; j < DAG_map[DAG_symbol_to_node[C]].front_node.size(); j++)
		{//�鿴C������ǰ�����
			if (DAG_map[DAG_map[DAG_symbol_to_node[C]].front_node[j]].operate_symbol == w)
			{//����һC����ǰ����㣬�������w����BwC�е�w
				return temp_node.node_number;//B C��ǰ����������w��ֱ�ӷ���
			}
		}
		DAG_map[DAG_symbol_to_node[C]].front_node.push_back(temp_node.node_number);//w����B����ǰ��
		return temp_node.node_number;
	}
	else
	{//ֻҪB C����һ�㲻�ڲ���ͼ�У���ôһ��û��BwC�ĵ�
		int flag_B = 0, flag_C = 0;//�Ƿ��½���B C���ı�־
		int insert_node_number = DAG_map.size();//������ǲ���������
		DAG_property temp_node_B, temp_node_C;
		if (!DAG_symbol_to_node.count(B))
		{//B��㲻����
			temp_node_B.node_number = insert_node_number;
			temp_node_B.main_mark = B;
			if ((B[0] == '\'') || (B[0] == '\"'))
				temp_node_B.symbol_size = 4;
			else if (B.size() == 1 && isdigit(B[0]))
				temp_node_B.symbol_size = 4;
			else if (B[1] == '.')
				temp_node_B.symbol_size = 8;
			else if (isdigit(B[0]))
				temp_node_B.symbol_size = 4;
			else if (symbol_table_list[function_number].symbol_property_table[get_symbol_number(B, function_number)].type->type_code == "double")
				temp_node_B.symbol_size = 8;
			else
				temp_node_B.symbol_size = 4;
			DAG_symbol_to_node.insert(pair<string, int>(B, temp_node_B.node_number));
			insert_node_number++;//����Ϊ��һ�����������
			flag_B = 1;
		}
		if (!DAG_symbol_to_node.count(C))
		{//C��㲻����
			temp_node_C.node_number = insert_node_number;
			temp_node_C.main_mark = C;
			if (C.size() == 1 && isdigit(C[0]))
				temp_node_C.symbol_size = 4;
			else if ((C[0] == '\'') || (C[0] == '\"'))
				temp_node_C.symbol_size = 4;
			else if (C[1] == '.')
				temp_node_C.symbol_size = 8;
			else if (isdigit(C[0]))
				temp_node_C.symbol_size = 4;
			else if (symbol_table_list[function_number].symbol_property_table[get_symbol_number(C, function_number)].type->type_code == "double")
				temp_node_C.symbol_size = 8;
			else
				temp_node_C.symbol_size = 4;
			DAG_symbol_to_node.insert(pair<string, int>(C, temp_node_C.node_number));
			insert_node_number++;//����Ϊ��һ�����������
			flag_C = 1;
		}
		DAG_property temp_node_w;
		temp_node_w.node_number = insert_node_number;
		temp_node_w.operate_symbol = w;//�½��������Ϊw
		//B C�����½����
		if (flag_B)
		{
			temp_node_B.front_node.push_back(insert_node_number);
			DAG_map.push_back(temp_node_B);
		}
		if (flag_C)
		{
			temp_node_C.front_node.push_back(insert_node_number);
			DAG_map.push_back(temp_node_C);
		}
		temp_node_w.rear_node.push_back(DAG_symbol_to_node[B]);
		temp_node_w.rear_node.push_back(DAG_symbol_to_node[C]);
		if ((DAG_map[DAG_symbol_to_node[B]].symbol_size != 8) && (DAG_map[DAG_symbol_to_node[C]].symbol_size != 8))
			temp_node_w.symbol_size = 4;
		else
			temp_node_w.symbol_size = 8;
		DAG_map.push_back(temp_node_w);
		return temp_node_w.node_number;
	}
}

void Code_Process::handle_main_other_order(int node)//����Ǻ�������ӵĴӱ��λ�ô���
{
	if (DAG_map[node].main_mark == "\0")
	{//��ǰ�����δ��ֵ��ֻ��һ����������ӱ��ֻ��һ���¼ӵĵ�
		DAG_map[node].main_mark = DAG_map[node].other_mark[0];
		DAG_map[node].other_mark.pop_back();
		return;
	}
	else if (const_number_flag(DAG_map[node].main_mark))
	{//�����Ϊ���������轻��
		return;
	}
	else if (const_number_flag(DAG_map[node].other_mark[DAG_map[node].other_mark.size() - 1]))
	{//�²���Ĵӱ��Ϊ������ֱ�ӽ���
		string temp_s = DAG_map[node].main_mark;
		DAG_map[node].main_mark = DAG_map[node].other_mark[DAG_map[node].other_mark.size() - 1];
		DAG_map[node].other_mark.pop_back();
		DAG_map[node].other_mark.push_back(temp_s);
		return;
	}
	else if (block_part_variable_flag(DAG_map[node].main_mark))
	{//�����Ϊ���ھֲ�������ֱ�ӽ���
		string temp_s = DAG_map[node].main_mark;
		DAG_map[node].main_mark = DAG_map[node].other_mark[DAG_map[node].other_mark.size() - 1];
		DAG_map[node].other_mark.pop_back();
		DAG_map[node].other_mark.push_back(temp_s);
		return;
	}
}

void Code_Process::handle_DAG()
{
	for (int i = 0; i < DAG_map.size(); i++)
	{
		if (!get_symbol_size[function_number].count(DAG_map[i].main_mark))
			get_symbol_size[function_number].insert(pair<string, int>(DAG_map[i].main_mark, DAG_map[i].symbol_size));
		if (DAG_map[i].rear_node.size() == 2)
		{
			temp_item.operation_symbol = DAG_map[i].operate_symbol;
			temp_item.first_target.operation_target_value = DAG_map[DAG_map[i].rear_node[0]].main_mark;
			temp_item.second_target.operation_target_value = DAG_map[DAG_map[i].rear_node[1]].main_mark;
			temp_item.result_target.result_target_value = DAG_map[i].main_mark;
			temp_f.push_back(temp_item);
		}
		else if (DAG_map[i].rear_node.size() == 1)
		{
			temp_item.operation_symbol = DAG_map[i].operate_symbol;
			temp_item.first_target.operation_target_value = DAG_map[DAG_map[i].rear_node[0]].main_mark;
			temp_item.second_target.operation_target_value = "_";
			temp_item.result_target.result_target_value = DAG_map[i].main_mark;
			temp_f.push_back(temp_item);
		}
		if (!DAG_map[i].other_mark.empty())
		{
			for (int j = 0; j < DAG_map[i].other_mark.size(); j++)
			{
				if (block_part_variable_flag(DAG_map[i].other_mark[j]) == 0)
				{
					temp_item.operation_symbol = "=";
					temp_item.first_target.operation_target_value = DAG_map[i].main_mark;
					temp_item.second_target.operation_target_value = "_";
					temp_item.result_target.result_target_value = DAG_map[i].other_mark[j];
					temp_f.push_back(temp_item);
				}
			}
		}
	}
}

int Code_Process::get_symbol_number(string s, int i)//����һ���ַ���s�ڷ��ű�i�е�λ��
{
	for (int j = 0; j < symbol_table_list[i].symbol_property_table.size(); j++)
	{
		if (symbol_table_list[i].symbol_property_table[j].name == s)
		{
			return j;
		}
	}
	return 0;
}