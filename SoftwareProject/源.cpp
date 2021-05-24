#include<iostream>
using namespace std;

int main() {
    double a, b, c;
    cout << "输入三条边的长度："<<endl;
    cin >> a >> b >> c;
    if (a + b <= c || a + c <= b || b + c <= a) {
        cout << "不是三角形" << endl;
    }
    else {
        if (a == b && a == c) {
            cout << "等边三角形" << endl;
        }
        else if (a == b || b == c || a == c) {
            cout << "等腰三角形" << endl;
        }
        else {
            cout << "一般三角形" << endl;
        }
    }
    return 0;
}