#include<iostream>
using namespace std;

int main() {
    double a, b, c;
    cout << "���������ߵĳ��ȣ�"<<endl;
    cin >> a >> b >> c;
    if (a + b <= c || a + c <= b || b + c <= a) {
        cout << "����������" << endl;
    }
    else {
        if (a == b && a == c) {
            cout << "�ȱ�������" << endl;
        }
        else if (a == b || b == c || a == c) {
            cout << "����������" << endl;
        }
        else {
            cout << "һ��������" << endl;
        }
    }
    return 0;
}