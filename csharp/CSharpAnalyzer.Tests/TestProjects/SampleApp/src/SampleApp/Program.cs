using SampleApp.Services;
using SampleApp.Models;

namespace SampleApp;

class Program
{
    static void Main(string[] args)
    {
        var service = new UserService();
        var user = new User("Alice", 30);

        service.PrintUser(user);
    }
}