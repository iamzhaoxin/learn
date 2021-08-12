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
//};//声明结构体类型
//
//char Vt[MaxVtNum]; //定义终极符号集
//char Vn[MaxVnNum]; //定义非终极符号集
//int  main()
//{
//	int n;//文法表达式数目
//	int x = 0;//非终极符号集元素个数
//	int y = 0;//终极符号集元素个数
//	cout << "请输入表达式数目n：";
//	cin >> n;
//	struct rule grammar[MaxRuleNum]; //定义文法
//	cout << endl;
//	char xy[2];
//
//	//输入文法
//	for (int i = 0; i < n; i++)
//	{
//		cout << "请输入第" << i+1 << "个文法右部长度：";
//		cin >> grammar[i].RLength;
//		cout << "请输入第" << i+1 << "个文法" << endl;
//		cin >> grammar[i].Left;
//		for (int x = 0; x < 2; x++)
//		{
//			cin >> xy[x];
//		}
//		//cout<<"请输入第"<<i<<"个文法右部：";
//		for (int j = 0; j < grammar[i].RLength; j++) //输入文法右部
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
//	//存储文法
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
//	//去重复字符串
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
//	cout << "开始符：" << grammar[0].Left << endl;
//	cout << "非终结符集：";
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
//	cout << "终结符集: ";
//	for (int i = 0; i < y; i++)
//	{
//		cout << Vt[i] << " ";
//	}
//	cout << endl;
//	return 0;
//}
//
