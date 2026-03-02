package com.kiraliza.spring.authenticaion.sso_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Arrays;
import java.util.List;

@Embeddable
public class PersonName
{
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "middle_name", length = 100)
    private String middleName;
    @Column(name = "prefix", length = 100)
    private String prefix;
    @Column(name = "suffix", length = 100)
    private String suffix;

    public PersonName()
    {
    }

    public PersonName(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonName(String firstName, String lastName, String middleName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public PersonName(String firstName, String lastName, String middleName, String prefix, String suffix)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public PersonName setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public PersonName setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public PersonName setMiddleName(String middleName)
    {
        this.middleName = middleName;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public PersonName setPrefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public PersonName setSuffix(String suffix)
    {
        this.suffix = suffix;
        return this;
    }

    public static PersonName parse( String compoundName )
    {
        if (compoundName == null || compoundName.trim().isEmpty())
        {
            return new PersonName("", "", "", "", "");
        }

        String prefix = "";
        String suffix = "";

        // List of possible prefixes and suffixes, extend as needed
        List<String> prefixes = Arrays.asList("Mr.", "Mrs.", "Ms.", "Dr.", "Prof.");
        List<String> suffixes = Arrays.asList("Jr.", "Sr.", "II", "III", "PhD");

        String[] parts = compoundName.trim().split("\\s+");

        int startIdx = 0;
        int endIdx = parts.length - 1;

        if (prefixes.stream().anyMatch(x -> x.equalsIgnoreCase(parts[0])))
        {
            prefix = parts[0];
            startIdx = 1;
        }

        final int finalEndIdx = endIdx;
        if (suffixes.stream().anyMatch(x -> x.equalsIgnoreCase(parts[finalEndIdx])))
        {
            suffix = parts[endIdx];
            endIdx--;
        }

        String lastName = endIdx >= startIdx ? parts[endIdx] : "";
        String firstName = "";
        String middleName = "";

        if (endIdx - startIdx >= 1)
        {
            firstName = parts[startIdx];
            if (endIdx - startIdx > 1)
            {
                StringBuilder middleBuilder = new StringBuilder();
                for (int i = startIdx + 1; i < endIdx; i++) {
                    middleBuilder.append(parts[i]).append(" ");
                }
                middleName = middleBuilder.toString().trim();
            }
        }
        else if (endIdx == startIdx)
        {
            firstName = parts[startIdx];
        }

        return new PersonName(firstName, lastName, middleName, suffix, prefix);
    }

    private static String replaceBrackets( String str )
    {
        return str == null ? str : str.replaceAll( "[(){}\\[\\]]", "" );
    }

}
