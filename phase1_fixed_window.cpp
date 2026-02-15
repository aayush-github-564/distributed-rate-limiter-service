#include <iostream>
#include <unordered_map>
#include <ctime>
#include <string>

using namespace std;

// Stores request info for one user
struct UserRequestData {
    int requestCount;
    time_t windowStartTime;

    UserRequestData() {
        requestCount = 0;
        windowStartTime = 0;
    }
};

class FixedWindowRateLimiter {
private:
    int maxRequests;
    int windowSize; // in seconds
    unordered_map<string, UserRequestData> userData;

public:
    FixedWindowRateLimiter(int maxReq, int windowSec) {
        maxRequests = maxReq;
        windowSize = windowSec;
    }

    bool allowRequest(string userId) {
        time_t currentTime = time(nullptr);
        cout << "DEBUG: currentTime = " << currentTime << endl;

        // If user not seen before, initialize
        if (userData.find(userId) == userData.end()) {
            userData[userId].requestCount = 1;
            userData[userId].windowStartTime = currentTime;
            return true;
        }

        UserRequestData &data = userData[userId];

        // Check if current window expired
        if (currentTime - data.windowStartTime >= windowSize) {
            // Reset window
            data.windowStartTime = currentTime;
            data.requestCount = 1;
            return true;
        }

        // Window still valid
        if (data.requestCount < maxRequests) {
            data.requestCount++;
            return true;
        }

        // Rate limit exceeded
        return false;
    }
};

int main() {
    int maxRequests = 5;
    int windowSize = 10; // seconds

    FixedWindowRateLimiter limiter(maxRequests, windowSize);

    string userId;
    cout << "Rate Limiter Started (Fixed Window)\n";
    cout << "Max Requests: " << maxRequests 
         << " | Window Size: " << windowSize << " seconds\n\n";

    while (true) {
        cout << "Enter user ID (or 'exit'): ";
        cin >> userId;

        if (userId == "exit") {
            break;
        }

        if (limiter.allowRequest(userId)) {
            cout << "Request ALLOWED ✅\n\n";
        } else {
            cout << "Request BLOCKED ❌ (Rate limit exceeded)\n\n";
        }
    }

    return 0;
}