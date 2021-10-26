#include <bits/stdc++.h>
using namespace std;

void fun(string ss, string s, vector<int> v, vector<string> &res);
int main()
{
    string s;
    cin >> s;

    vector<string> res;
    vector<int> v(s.length());
    string ss = "";
    fun(ss, s, v, res);

    cout<<res.size();
    return 0;
}
void fun(string ss, string s, vector<int> v, vector<string> &res)
{
    for (int i = 0; i < s.length(); i++)
    {
        if (v[i])
            continue;
        else
        {
            v[i] = true;
            ss += s.substr(i, 1);
            if (ss.length() == s.length())
            {
                if (find(res.begin(), res.end(), ss) != res.end())
                    return;
                else
                {
                    res.push_back(ss);
                    return;
                }
            }
            fun(ss, s, v, res);
            v[i]=false;
            ss.erase(ss.end()-1);
        }
    }
}