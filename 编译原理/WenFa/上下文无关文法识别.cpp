//#include<iostream>
//using namespace std;
//
//
//const int MaxRightLenth = 20;
//const int MaxRuleNum = 20;
//const int MaxVtNum = 20;
//const int MaxVnNum = 20;
//
//struct rule
//{
//	char Left;
//	char Right[MaxRightLenth];
//	int RLength;
//};//�����ṹ������
//
//char Vt[MaxVtNum]; //�����ռ����ż�
//char Vn[MaxVnNum]; //������ռ����ż�
//int  main()
//{
//	int n;//�ķ����ʽ��Ŀ
//	int x = 0;//���ռ����ż�Ԫ�ظ���
//	int y = 0;//�ռ����ż�Ԫ�ظ���
//	cout << "��������ʽ��Ŀn��";
//	cin >> n;
//	struct rule grammar[MaxRuleNum]; //�����ķ�
//	cout << endl;
//	char xy[2];
//
//	//�����ķ�
//	for (int i = 0; i < n; i++)
//	{
//		cout << "�������" << i+1 << "���ķ��Ҳ����ȣ�";
//		cin >> grammar[i].RLength;
//		cout << "�������" << i+1 << "���ķ�" << endl;
//		cin >> grammar[i].Left;
//		for (int x = 0; x < 2; x++)
//		{
//			cin >> xy[x];
//		}
//		//cout<<"�������"<<i<<"���ķ��Ҳ���";
//		for (int j = 0; j < grammar[i].RLength; j++) //�����ķ��Ҳ�
//		{
//			cin >> grammar[i].Right[j];
//		}
//		cout << endl;
//		for (x = 0; x < 2; x++)
//		{
//			xy[x] = '\0';
//		}
//	}
//
//	//�洢�ķ�
//	for (int i = 0; i < n; i++)
//	{
//		if (grammar[i].Left >= 'A' && grammar[i].Left <= 'Z')
//		{
//			Vn[x] = grammar[i].Left;
//			x++;
//		}
//		for (int j = 0; j < grammar[i].RLength; j++)
//		{
//			if (grammar[i].Right[j] >= 'A' && grammar[i].Right[j] <= 'Z')
//			{
//				Vn[x] = grammar[i].Right[j];
//				x++;
//			}
//			else if (grammar[i].Right[j] != '|' && grammar[i].Right[j] != '&')
//			{
//				Vt[y] = grammar[i].Right[j];
//				y++;
//			}
//		}
//	}
//
//	//ȥ�ظ��ַ���
//	for (int i = 0; i < x; i++)
//	{
//		for (int j = i + 1; j < x; j++)
//		{
//			if (Vn[i] == Vn[j])
//			{
//				Vn[j] = '\0';
//			}
//		}
//	}
//
//	for (int i = 0; i < y; i++)
//	{
//		for (int j = i + 1; j < y; j++)
//		{
//			if (Vt[i] == Vn[j])
//			{
//				Vt[j] = '\0';
//			}
//		}
//	}
//
//	cout << "��ʼ����" << grammar[0].Left << endl;
//	cout << "���ս������";
//	for (int i = 0; i < x; i++)
//	{
//		if (Vn[i] == '\0')
//		{
//			continue;
//		}
//		cout << Vn[i];
//		cout << " ";
//	}
//	cout << endl;
//	cout << "�ս����: ";
//	for (int i = 0; i < y; i++)
//	{
//		cout << Vt[i] << " ";
//	}
//	cout << endl;
//	return 0;
//}
//
