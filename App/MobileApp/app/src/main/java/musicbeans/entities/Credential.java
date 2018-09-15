package musicbeans.entities;

public class Credential
{
    private static Account account;

    public static void setCredentials(Account credential){account=credential;}

    public static Account getCredentials() {return account;}
}
