#include "Lexical_Analysis.h"

Lexical_Analysis::Lexical_Analysis()
{
	token_list.resize(0);
	token_type.resize(0);
	const_symbol.resize(0);
	const_number.resize(0);
	const_char.resize(0);
	const_string.resize(0);
}

Lexical_Analysis::~Lexical_Analysis()
{
	code_file.close();
};

void Lexical_Analysis::Generate_Token() //�ʷ�������
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
			break;
		}
		code_file >> code_char;//������һ���ַ�
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
				token[i++] = code_char;									   //�������ַ�����token�����ַ�,
			//cout << token << endl;
		}
	}
	token_list.push_back("#");
	token_type.push_back("������");
	token_number.push_back(1);
}

int Lexical_Analysis::change_state(int state, char ch) //״̬ת��
{
	switch (state)
	{
	case 0: //��ʼ̬
	{
		switch (ch)
		{
		case ' ':
			return 0;
		case '\n':
			return 0;
		case '\t':
			return 0;
		case '\r':
			return 0;
		case 'a':
			return 1;
		case '0':
			return 2;
		case '(':
			return 3;
		case ')':
			return 3;
		case '[':
			return 3;
		case ']':
			return 3;
		case '{':
			return 3;
		case '}':
			return 3;
		case '\'':
			return 9;
		case '\"':
			return 10;
		case ',':
			return 11;
		case ';':
			return 12;
		case '<':
			return 13;
		case '>':
			return 15;
		case '=':
			return 17;
		case '+':
			return 19;
		case '-':
			return 21;
		case '*':
			return 23;
		case '/':
			return 24;
		case '|':
			return 31;
		case '&':
			return 33;
		case '!':
			return 37;
		case ':':
			return 39;
		case '^':
			return 40;
		case '#':
			return 40;
		case '%':
			return 40;
		case '~':
			return 40;
		case '$':
			return 40;
		case '@':
			return 40;
		case '.':
			return 40;
		case '?':
			return 40;
		case '_':
			return 1;
		case '\\':
			return 47;
		default:
			break;
		}
	}
	case 1: //����ĸ
	{
		switch (ch)
		{
		default:
			return 101;
			break;
		case 'a':
			return 1;
		case '0':
			return 1;
		case '_':
			return 1;
			break;
		}
	}
	case 2: //������
		switch (ch)
		{
		default:
			return 104;
		case '0':
			return 2;
		case '.':
			return 27;
		case 'e':
			return 28;
		}
	case 3: //��������
		return 102;
	case 9: //ʶ���ַ�
		switch (ch)
		{
		case '0':
			return 9;
		case 'a':
			return 9;
		case '\'':
			return 25;
		default:
			break;
		}
	case 25:
		return 105;
	default:
		break;
	case 10: //ʶ���ַ���
		switch (ch)
		{
		case '0':
			return 10;
		case 'a':
			return 10;
		case '\"':
			return 26;
		default:
			break;
		}
	case 26:
		return 106;
	case 11: //����
		return 102;
	case 12: //�ֺ�
		return 102;
	case 13: //��һ��С�ں�
		switch (ch)
		{
		default: //<
			return 102;
		case '=':
			return 14;
		case '<':
			return 35;
		}
	case 14: //<=
		return 102;
	case 15: //��һ�����ں�
		switch (ch)
		{
		default: //>
			return 102;
		case '=':
			return 16;
		case '>':
			return 36;
		}
	case 16: //>=
		return 102;
	case 17: //��һ�����ں�
		switch (ch)
		{
		default: //=
			return 102;
		case '=':
			return 18;
		}
	case 18: //==
		return 102;
	case 19:
		switch (ch)
		{
		case '+':
			return 20;
		default: //+
			return 102;
		}
	case 20: //++
		return 102;
	case 21:
		switch (ch)
		{
		case '-':
			return 22;
		case '>':
			return 48;
		default: //-
			return 102;
		}
	case 22: //--
		return 102;
	case 23: //�˺�
		return 102;
	case 24: //����
		switch (ch)
		{
		case '/'://"//"
			return 41;
		case '*':
			return 42;
		default:
			return 102;
		}
	case 27:
		switch (ch)
		{
		case '0':
			return 27;
		default:
			return 104;
		}
	case 28:
		switch (ch)
		{
		case '0':
			return 30;
		case '-':
			return 29;
		default:
			return 104;
		}
	case 29:
		switch (ch)
		{
		case '0':
			return 29;
		default:
			return 104;
		}
	case 30: //ָ������Ϊ��
		switch (ch)
		{
		case '0':
			return 30;
		default:
			return 104;
		}
	case 31://"|"
		switch (ch)
		{
		case '|':
			return 32;
		default:
			return 102;
		}
	case 32://"||"
		return 102;
	case 33://"&"
		switch (ch)
		{
		case '&':
			return 34;
		default:
			return 102;
		}
	case 34://"&&"
		return 102;
	case 35://"<<"
		return 102;
	case 36://">>"
		return 102;
	case 37://"!"
		switch (ch)
		{
		case '=':
			return 38;
		default:
			return 102;
		}
	case 38://"!="
		return 102;
	case 39://":"
		switch (ch)
		{
		case ':':
			return 46;
		default://":"
			return 102;
		}
	case 40://"^","#","%","~","@","$","?","."
		return 102;
	case 41://"//"
		return 102;
	case 42://"/*"
		switch (ch)
		{
		case '*':
			return 43;
		default:
			return 42;
		}
	case 43://ע�ͽ���������һ����*��
		switch (ch)
		{
		case '/':
			return 44;
		default:
			return 42;
		}
	case 44://"*/"
		return 102;
	case 45://"."
		return 102;
	case 46://"::"
		return 102;
	case 47://"\\"
		switch (ch)
		{
		case '\'':
			return 47;
		case '\"':
			return 47;
		default:
			return 102;
		}
	case 48://"->"
		return 102;
	}

	return 0;
}


void Lexical_Analysis::check_code(int state, char* token) //���ɵ���
{
	switch (state) //������֪״̬����ͬ������
	{
	case (101): //�ؼ��ֻ��ʶ��
		if (!search_key(token, &key_word, "�ؼ���"))
			search_key(token, &const_symbol, "��ʶ��");
		break;
	case (102): //�ַ�
		search_key(token, &key_symbol, "�ؼ�����");
		break;
	case (103): //��ʶ��
		search_key(token, &const_symbol, "��ʶ��");
		break;
	case (104): //����
		search_key(token, &const_number, "����");
		break;
	case (105): //�ַ�
		search_key(token, &const_char, "�ַ�");
		break;
	case (106): //�ַ���
		search_key(token, &const_string, "�ַ���");
		break;
	default:
		break;
	}
}

int Lexical_Analysis::search_key(char* token, vector<string>* key, string type) //���Ҳ�����token����
{
	for (vector<int>::size_type i = 0; i < (*key).size(); i++) //�������ұ�
	{
		if ((*key)[i] == token) //ƥ��
		{
			token_list.push_back(token); //����token����
			token_type.push_back(type);
			token_number.push_back(i);
			return 1; //����
		}
	}
	if (type != "�ؼ���" && type != "�ؼ�����") //δƥ�䣬�ض����û��������
	{
		(*key).push_back(token);	 //������ұ�
		token_list.push_back(token); //����token����
		token_type.push_back(type);
		token_number.push_back((*key).size());
	}
	return 0;
}

void Lexical_Analysis::print_token_list()
{
	{
		cout << "����\t���\t����" << endl;
		for (vector<int>::size_type i = 0; i < token_list.size(); i++)
		{
			cout << token_type[i] << "\t" << token_number[i] << "\t" << token_list[i] << endl;
		}
		cout << endl;
	}
}