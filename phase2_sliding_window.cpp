#include <iostream>
#include <unordered_map>
#include <queue>
#include <string>

using namespace std;

class SlidingWindowRateLimiter {
private:
    int maxRequests;
    int windowSize; // seconds

    // user -> queue of request timestamps
    unordered_map<string, queue<int>> userRequests;

public:
    SlidingWindowRateLimiter(int maxReq, int windowSec) {
        maxRequests = maxReq;
        windowSize = windowSec;
    }

    bool allowRequest(string userId, int currentTime) {
        queue<int> &q = userRequests[userId];

        // Remove requests outside the sliding window
        while (!q.empty() && q.front() <= currentTime - windowSize) {
            q.pop();
        }

        // If limit reached, block
        if (q.size() >= maxRequests) {
            return false;
        }

        // Otherwise allow and record this request
        q.push(currentTime);
        return true;
    }
};

int main() {
    int maxRequests = 3;
    int windowSize = 10;

    SlidingWindowRateLimiter limiter(maxRequests, windowSize);

    int n;
    cout << "Enter number of requests: ";
    cin >> n;

    for (int i = 0; i < n; i++) {
        string user;
        int time;

        cout << "Enter request (user time): ";
        cin >> user >> time;

        bool allowed = limiter.allowRequest(user, time);

        if (allowed) {
            cout << "Request " << i << ": ALLOWED\n";
        } else {
            cout << "Request " << i << ": BLOCKED\n";
        }
    }

    return 0;
}   