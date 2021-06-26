#include "Grammar_Analysis.h"

Grammar_Analysis::Grammar_Analysis()
{
	production_number = 0;
	first_list.resize(not_end_symbol.size());
	follow_list.resize(not_end_symbol.size());
	select_list.resize(production_number + 1);
	LL1_list.resize(not_end_symbol.size() + 1);
	for (vector<char>::size_type i = 0; i < LL1_list.size(); i++)
	{
		LL1_list[i].resize(end_symbol.size() + 1);
	}
	for (vector<int>::size_type i = 1; i <= not_end_symbol.size(); i++)
	{
		for (vector<int>::size_type j = 1; j <= end_symbol.size(); j++)
		{
			LL1_list[i][j] = "empty";
		}
	}
	for (vector<int>::size_type i = 1; i < LL1_list.size() && i != not_end_symbol.size(); i++)
	{
		LL1_list[i][0] = not_end_symbol[i];
	}
	for (vector<int>::size_type i = 1; i < LL1_list[0].size() && i != end_symbol.size(); i++)
	{
		LL1_list[0][i] = end_symbol[i];
		if (i == end_symbol.size() - 1)
		{
			LL1_list[0][i + 1] = "#";
		}
	}
}

Grammar_Analysis::~Grammar_Analysis() {}

void Grammar_Analysis::Generate_Token() //�ʷ�������
{
	code_file.open("Code_Information.txt");
	if (code_file.is_open())
		cout << "Դ�����ļ�:Code_Information.txt" << endl;
	char code_char, token[token_max_size]; //ÿ�ζ�����ַ�/����м�token��
	memset(token, '\0', sizeof(token));
	int i = 0, state = 0, code_end = 1;
	code_file >> noskipws; //ǿ�ƶ���ո�ͻس�
	while (code_file.peek() != EOF || code_end)
	{
		if (code_file.peek() == EOF && state < 100)
		{
			code_end = 0;
			state = change_state(state, '\n');
			check_code(state, token);
			if (!LL1_sentence_analysis(token))
			{
				system("pause");
				exit(0);
			}
			break;
		}
		code_file >> code_char;																					//������һ���ַ�
		if (((code_char <= 'z' && code_char >= 'a') || (code_char <= 'Z' && code_char >= 'A')) && (state != 2)) //��ĸ
			state = change_state(state, 'a');
		else if (code_char <= '9' && code_char >= '0') //����
			state = change_state(state, '0');
		else
			state = change_state(state, code_char); //������һ���ַ�����״̬ת��
		if (state > 100)							//�ս�̬
		{
			//cout << state << ' ' << token << endl;
			check_code(state, token); //�жϵ���
			if (!LL1_sentence_analysis(token))
			{
				system("pause");
				exit(0);
			}
			i = 0;
			state = 0;
			if ((code_char <= 'z' && code_char >= 'a') || (code_char <= 'Z' && code_char >= 'A')) //Сд��ĸ
				state = change_state(state, 'a');
			else if (code_char <= '9' && code_char >= '0') //����
				state = change_state(state, '0');
			else
				state = change_state(state, code_char);
			memset(token, '\0', sizeof(token));
			if (code_char != ' ' && code_char != '\n')
				token[i++] = code_char; //�������ַ��������ú��token�����ַ�
		}
		else
		{
			if (code_char != ' ' && code_char != '\n') //���ս�̬��
				token[i++] = code_char;				   //�������ַ�����token�����ַ�,
													   //cout << token << endl;
		}
	}
	token_list.push_back("#");
	token_type.push_back("������");
	token_number.push_back(1);
	LL1_sentence_analysis("#");
}

void Grammar_Analysis::get_first_list() //��first��
{
	int flag = 1; //����ѭ����־
	while (flag == 1)
	{
		flag = 0;
		for (int i = 0; i < production_number; i++) //�������в���ʽ
		{
			if (production_right[i].size() == 0)
			{ //�մ���select��Ϊ��
				continue;
			}
			else if (get_end_symbol_number(production_right[i][0]))
			{ //����̬���ս��
				if (new_symbol_flag(&first_list, production_right[i][0], get_not_end_symbol_number(production_left[i])))
				{
					flag = 1;
					first_list[get_not_end_symbol_number(production_left[i])].push_back(production_right[i][0]);
				}
			}
			else if (get_not_end_symbol_number(production_right[i][0]))
			{ //�����Ǳ�ķ��ս��
				int j = 0;
				while (empty_symbol_flag(production_right[i][j]))
				{
					for (vector<char>::size_type k = 0; k < first_list[get_not_end_symbol_number(production_right[i][j])].size(); k++)
					{
						if (new_symbol_flag(&first_list, first_list[get_not_end_symbol_number(production_right[i][j])][k], get_not_end_symbol_number(production_left[i])))
						{
							flag = 1;
							first_list[get_not_end_symbol_number(production_left[i])].push_back(first_list[get_not_end_symbol_number(production_right[i][j])][k]);
						}
					}
					if (j == production_right[i].size() - 1)
						break;
					else
						j++;
				}
				if (get_end_symbol_number(production_right[i][j]))
				{
					if (new_symbol_flag(&first_list, production_right[i][j], get_not_end_symbol_number(production_left[i])))
					{
						first_list[get_not_end_symbol_number(production_left[i])].push_back(production_right[i][j]);
						flag = 1;
					}
				}
				else
				{
					for (vector<char>::size_type k = 0; k < first_list[get_not_end_symbol_number(production_right[i][j])].size(); k++)
					{
						if (new_symbol_flag(&first_list, first_list[get_not_end_symbol_number(production_right[i][j])][k], get_not_end_symbol_number(production_left[i])))
						{
							flag = 1;
							first_list[get_not_end_symbol_number(production_left[i])].push_back(first_list[get_not_end_symbol_number(production_right[i][j])][k]);
						}
					}
				}
			}
		}
	}
}

void Grammar_Analysis::get_follow_list() //��follow��
{
	follow_list[get_not_end_symbol_number(head_symbol)].push_back("#");
	int flag = 1;
	while (flag == 1)
	{
		flag = 0;
		for (int i = 0; i < production_number; i++)
		{ //�������в���ʽ
			for (vector<char>::size_type j = 0; j < production_right[i].size(); j++)
			{ //���β鿴����ʽ���Ҳ�
				if (get_not_end_symbol_number(production_right[i][j]))
				{ //�������ս��
					if (j == production_right[i].size() - 1)
					{ //��ǰΪ���һ��
						for (vector<char>::size_type k = 0; k < follow_list[get_not_end_symbol_number(production_left[i])].size(); k++)
						{ //���һ�������ʽ�󲿵�follow���������ʽ�Ҳ����һ���follow��
							if (new_symbol_flag(&follow_list, follow_list[get_not_end_symbol_number(production_left[i])][k], get_not_end_symbol_number(production_right[i][j])))
							{
								flag = 1;
								follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(follow_list[get_not_end_symbol_number(production_left[i])][k]);
							}
						}
					}
					else if (get_end_symbol_number(production_right[i][j + 1]))
					{ //��һ��Ϊ�ս��
						if (new_symbol_flag(&follow_list, production_right[i][j + 1], get_not_end_symbol_number(production_right[i][j])))
						{ //��һ��Ϊ�ս��������ǰһ����ս����follow��
							flag = 1;
							follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(production_right[i][j + 1]);
						}
					}
					else if (get_not_end_symbol_number(production_right[i][j + 1]))
					{ //��һ��Ϊ���ս��
						int k = j;
						while (k != production_right[i].size() - 1)
						{
							k++;
							if (get_end_symbol_number(production_right[i][k]))
							{ //��һ��Ϊ�ս��������ǰһ����ս����follow��
								if (new_symbol_flag(&follow_list, production_right[i][j + 1], get_not_end_symbol_number(production_right[i][j])))
								{
									flag = 1;
									follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(production_right[i][j + 1]);
								}
								break;
							}
							else if (get_not_end_symbol_number(production_right[i][k]))
							{ //��һ��Ϊ���ս��
								if (empty_symbol_flag(production_right[i][k]))
								{ //��һ���ս���ɿ�
									for (vector<char>::size_type l = 0; l < first_list[get_not_end_symbol_number(production_right[i][k])].size(); l++)
									{ //��һ���first������ǰһ���follow��
										if (new_symbol_flag(&follow_list, first_list[get_not_end_symbol_number(production_right[i][k])][l], get_not_end_symbol_number(production_right[i][j])))
										{
											flag = 1;
											follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(first_list[get_not_end_symbol_number(production_right[i][k])][l]);
										}
									}
									if (k == production_right[i].size() - 1)
									{ //���һ�������ʽ�󲿵�follow���������ʽ�Ҳ����һ���follow��
										for (vector<char>::size_type l = 0; l < follow_list[get_not_end_symbol_number(production_left[i])].size(); l++)
										{
											if (new_symbol_flag(&follow_list, follow_list[get_not_end_symbol_number(production_left[i])][l], get_not_end_symbol_number(production_right[i][j])))
											{
												flag = 1;
												follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(follow_list[get_not_end_symbol_number(production_left[i])][l]);
											}
										}
									}
								}
								else if (!empty_symbol_flag(production_right[i][k]) && k == j + 1)
								{ //��һ����ս���ǿ�
									for (vector<char>::size_type l = 0; l < first_list[get_not_end_symbol_number(production_right[i][k])].size(); l++)
									{ //��һ����ս��first����ǰһ���follow��
										if (new_symbol_flag(&follow_list, first_list[get_not_end_symbol_number(production_right[i][k])][l], get_not_end_symbol_number(production_right[i][j])))
										{
											flag = 1;
											follow_list[get_not_end_symbol_number(production_right[i][j])].push_back(first_list[get_not_end_symbol_number(production_right[i][k])][l]);
										}
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}

void Grammar_Analysis::get_select_list() //��select��
{
	vector<char>::size_type production_right_empty_symbol = 0;
	for (int i = 0; i < production_number; i++)
	{ //�������в���ʽ
		production_right_empty_symbol = 0;
		if (production_right[i].size() == 0)
		{ //�Ҳ�����ʽΪ�մ�,x�Ų���ʽ��select��Ϊ���󲿵�follow��
			for (vector<char>::size_type j = 0; j < follow_list[get_not_end_symbol_number(production_left[i])].size(); j++)
			{
				if (new_symbol_flag(&select_list, follow_list[get_not_end_symbol_number(production_left[i])][j], i + 1))
				{
					select_list[i + 1].push_back(follow_list[get_not_end_symbol_number(production_left[i])][j]);
				}
			}
			continue;
		}
		else
		{ //�Ҳ�����ʽ������һ���ַ�
			for (vector<char>::size_type j = 0; j < production_right[i].size(); j++)
			{ //��������ʽ��ÿ������
				if (get_end_symbol_number(production_right[i][j]))
				{ //�����ս�����Ҳ�����ʽ�ǿգ����׷���Ϊ�ս��
					if (new_symbol_flag(&select_list, production_right[i][j], get_not_end_symbol_number(production_left[i])) + 1)
					{ //x�Ų���ʽ��select��Ϊ���ս��
						select_list[i + 1].push_back(production_right[i][j]);
					}
					break;
				}
				else if (get_not_end_symbol_number(production_right[i][j]))
				{ //�Ҳ�����ʽ�������ս��
					if (get_end_symbol_number(production_right[i][j]))
					{
						if (new_symbol_flag(&select_list, production_right[i][j], get_not_end_symbol_number(production_left[i])) + 1)
						{ //x�Ų���ʽ��select��Ϊ���ս��
							select_list[i + 1].push_back(production_right[i][j]);
						}
						break;
					}
					else if (!empty_symbol_flag(production_right[i][j]))
					{ //�ǿյķ��ս��
						if (j == production_right_empty_symbol)
						{ //��һ��Ϊ�ǿշ��ս������first������follow��
							for (vector<char>::size_type k = 0; k < first_list[get_not_end_symbol_number(production_right[i][j])].size(); k++)
							{
								if (new_symbol_flag(&select_list, first_list[get_not_end_symbol_number(production_right[i][j])][k], i + 1))
								{ //x�Ų���ʽ��select��Ϊ���󲿵�first��
									select_list[i + 1].push_back(first_list[get_not_end_symbol_number(production_right[i][j])][k]);
								}
							}
						}
						break;
					}
					else
					{
						for (vector<char>::size_type k = 0; k < first_list[get_not_end_symbol_number(production_right[i][j])].size(); k++)
						{
							if (new_symbol_flag(&select_list, first_list[get_not_end_symbol_number(production_right[i][j])][k], i + 1))
							{ //x�Ų���ʽ��select��Ϊ���󲿵�first��
								select_list[i + 1].push_back(first_list[get_not_end_symbol_number(production_right[i][j])][k]);
							}
						}
						production_right_empty_symbol++;
						if (production_right_empty_symbol == production_right[i].size())
						{
							for (vector<char>::size_type j = 0; j < follow_list[get_not_end_symbol_number(production_left[i])].size(); j++)
							{
								if (new_symbol_flag(&select_list, follow_list[get_not_end_symbol_number(production_left[i])][j], i + 1))
								{ //x�Ų���ʽ��select��Ϊ���󲿵�first��
									select_list[i + 1].push_back(follow_list[get_not_end_symbol_number(production_left[i])][j]);
								}
							}
							break;
						}
					}
				}
			}
		}
	}
}

void Grammar_Analysis::get_LL1_list() //��LL1������
{
	for (int i = 1; i <= production_number; i++)
	{
		for (vector<char>::size_type j = 0; j < select_list[i].size(); j++)
		{
			LL1_list[get_not_end_symbol_number(production_left[i - 1])][get_end_symbol_number(select_list[i][j])] = to_string(i);
		}
	}
}

int Grammar_Analysis::LL1_sentence_analysis(string token) //LL1�﷨����
{
	static int first_flag = 1, step_number = 1;
	/*cout << "LL1����������ʶ��:";
	for (vector<int>::size_type i = 0; i < token_list.size(); i++)
	{
		cout << token_list[i];
	}*/
	if (first_flag == 1)
	{
		first_flag = 0;
		cout << "\n����\tջ��\t����\t����" << endl;
		grammar_stack.push("#");
		//cout << step_number++ << "\t��\t��\t������'#'��ջ" << endl;
		grammar_stack.push(head_symbol);
		//cout << step_number++ << "\t'#'\t��\t��ʼ��'" << head_symbol << "'��ջ" << endl;
	}
	while (!grammar_stack.empty()) //ջ�ǿ�
	{
		if (get_end_symbol_number(grammar_stack.top()))
		{ //ջ������Ϊ�ս��
			if (grammar_stack.top() == get_next_w_type(token))
			{ //���ڵ�ǰ�����ַ�
				cout << step_number++ << "\t" << grammar_stack.top() << "\t" << token << "\t'" << token << "'ƥ��ɹ���!" << endl;
				if (get_next_w_type(token) == "#")
				{
					cout << "�þ���ʶ��ɹ���\n" << endl;
					return 2;
				}
				grammar_stack.pop(); //��ջ
				return 1;
			}
			else //ʶ�𲻳ɹ�
			{
				cout << step_number++ << "\t" << grammar_stack.top() << "\t" << token << "\t'" << token << "'ƥ��ʧ�ܡ�!" << endl;
				cout << "�þ��Ӳ��ܱ�ʶ��\n" << endl;
				return 0;
			}
		}
		else if (get_not_end_symbol_number(grammar_stack.top()))
		{ //ջ������Ϊ���ս��
			if (get_end_symbol_number(get_next_w_type(token)))
			{
				if (LL1_list[get_not_end_symbol_number(grammar_stack.top())][get_end_symbol_number(get_next_w_type(token))] != "empty")
				{
					int i = atoi(LL1_list[get_not_end_symbol_number(grammar_stack.top())][get_end_symbol_number(get_next_w_type(token))].c_str());
					//cout << step_number++ << "\t" << grammar_stack.top() << "\t" << token << "\t'" << grammar_stack.top() << "'��ջ" << endl;
					grammar_stack.pop();
					for (int j = production_right[i - 1].size() - 1; j >= 0; j--)
					{
						//cout << "\t" << grammar_stack.top() << "\t" << token << "\t'" << production_right[i - 1][j] << "'��ջ" << endl;
						grammar_stack.push(production_right[i - 1][j]);
					}
				}
				else
				{
					cout << step_number++ << "\t" << grammar_stack.top() << "\t" << token << "\t'" << token << "'ƥ��ʧ�ܡ�!" << endl;
					cout << "�þ��Ӳ��ܱ�ʶ��\n" << endl;
					return 0;
				}
			}
			else
			{
				cout << step_number++ << "\t" << grammar_stack.top() << "\t" << token << "\t'" << token << "'ƥ��ʧ�ܡ�!" << endl;
				cout << "�þ��Ӳ��ܱ�ʶ��\n" << endl;
				return 0;
			}
		}
	}
	return 0;
}

void Grammar_Analysis::scanf_information() //����
{
	fstream fp;
	string str;
	string head;
	vector<string> right;
	fp.open("Grammar_Information.txt");
	if (!fp.is_open())
	{
		cout << "Can't find the file.\n";
		system("pause");
	}
	fp >> str;
	head = str;
	head_symbol = str;
	while (!fp.eof())
	{
		fp >> str;
		production_left.push_back(head);
		if (str == "->")
		{
			fp >> str;
			while (str != "#")
			{
				right.push_back(str);
				fp >> str;
			}
			production_right.push_back(right);
			right.clear();
		}
		fp >> str;
		head = str;
	}
	production_number = production_left.size();
	select_list.resize(production_number + 1);
}

void Grammar_Analysis::print_information() //���
{
	// ������в���ʽ
	cout << "������ݹ����ķ�����" << production_number << "������ʽ:" << endl;
	for (int i = 0; i < production_number; i++)
	{
		cout << i + 1 << "�Ų���ʽ:" << production_left[i] << "��";
		for (vector<char>::size_type j = 0; j < production_right[i].size(); j++)
		{
			cout << production_right[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
	// ������������Ƴ��ղ���ʽ�ķ���
	cout << "�������Ƴ��ղ���ʽ�ķ���:";
	for (vector<char>::size_type i = 1; i < not_end_symbol.size(); i++)
	{
		if (empty_symbol_flag(not_end_symbol[i]))
			cout << not_end_symbol[i] << " ";
	}
	cout << "\n\n";
	// ���first��
	cout << "first����:" << endl;
	for (vector<char>::size_type i = 1; i < first_list.size(); i++)
	{
		cout << "first(" << not_end_symbol[i] << "):";
		for (vector<char>::size_type j = 0; j < first_list[i].size(); j++)
		{
			cout << first_list[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
	cout << "follow����:" << endl;
	for (vector<char>::size_type i = 1; i < first_list.size(); i++)
	{
		cout << "follow(" << not_end_symbol[i] << "):";
		for (vector<char>::size_type j = 0; j < follow_list[i].size(); j++)
		{
			cout << follow_list[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
	cout << "select����:" << endl;
	for (int i = 1; i <= production_number; i++)
	{
		cout << "select(" << i << "):";
		for (vector<char>::size_type j = 0; j < select_list[i].size(); j++)
		{
			cout << select_list[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
	//// ���LL1������
	//cout << "LL1������:" << endl;
	//for (vector<int>::size_type i = 0; i < LL1_list.size(); i++)
	//{
	//	for (vector<int>::size_type j = 0; j < LL1_list[i].size(); j++)
	//	{
	//		cout << LL1_list[i][j] << "\t";
	//	}
	//	cout << endl;
	//}
	//cout << endl;
}

int Grammar_Analysis::get_not_end_symbol_number(string s) //���ƥ��ɹ������ط��ս�������
{
	for (vector<int>::size_type i = 0; i < not_end_symbol.size(); i++)
	{
		if (not_end_symbol[i] == s)
		{
			return i;
		}
	}
	return 0;
}

int Grammar_Analysis::get_end_symbol_number(string s) //���ƥ��ɹ��������ս�������
{
	for (vector<int>::size_type i = 0; i < end_symbol.size(); i++)
	{
		if (end_symbol[i] == s)
		{
			return i;
		}
	}
	return 0;
}

string Grammar_Analysis::get_next_w_type(string s) //����������Ŷ�Ӧ�ķ��ս��
{
	for (vector<int>::size_type i = 0; i < const_symbol.size(); i++)
	{
		if (const_symbol[i] == s)
		{
			return "��ʶ��";
		}
	}
	for (vector<int>::size_type i = 0; i < const_number.size(); i++)
	{
		if (const_number[i] == s)
		{
			return "��ֵ";
		}
	}
	for (vector<int>::size_type i = 0; i < const_char.size(); i++)
	{
		if (const_char[i] == s)
		{
			return "�ַ�";
		}
	}
	for (vector<int>::size_type i = 0; i < const_string.size(); i++)
	{
		if (const_string[i] == s)
		{
			return "�ַ���";
		}
	}
	return s;
}

int Grammar_Analysis::new_symbol_flag(vector<vector<string>>* symbol, string s, vector<char>::size_type i) //����i���(*symbol)����s�Ƿ����·���
{
	for (vector<char>::size_type j = 0; j < (*symbol)[i].size(); j++)
	{
		if ((*symbol)[i][j] == s)
			return 0;
	}
	return 1;
}

int Grammar_Analysis::empty_symbol_flag(string s) //�ж�һ�����ս���Ƿ��Ϊ��
{
	for (int i = 0; i < production_number; i++) //�������в���ʽ
	{
		if (production_left[i] == s) //�ڲ���ʽ�󲿲����ж��ַ�
		{
			if (production_right[i].size() == 0) //Ϊ��ֱ�ӷ���
				return 1;
			else
			{
				for (vector<char>::size_type j = 0; j < production_right[i].size(); j++)
				{
					if (get_not_end_symbol_number(production_right[i][j]))
					{
						if (!empty_symbol_flag(production_right[i][j]))
						{
							break;
						}
						else if (j == production_right[i].size() - 1) //���һ����ȻΪ��
							return 1;
					}
					else //�ս���ز�Ϊ�գ�������һ�β����ж�
						break;
				}
			}
		}
	}
	return 0;
}
