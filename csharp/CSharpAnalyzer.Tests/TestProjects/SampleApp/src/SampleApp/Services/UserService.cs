using SampleApp.Models;

namespace SampleApp.Services;

public class UserService : IUserService
{
    public void PrintUser(User user)
    {
        Console.WriteLine($"{user.Name} ({user.Age})");
    }
}