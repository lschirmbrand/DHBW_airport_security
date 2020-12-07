package security;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import security.employee.*;
import security.passenger.HandBaggage;
import security.passenger.Passenger;
import security.police.FederalPoliceOfficer;
import security.scanner.*;
import security.scanner.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BaggageScannerTest {

    Builder builder;
    BaggageScanner scanner;
    List<Passenger> passengers;

    public BaggageScannerTest() {
        builder = new Builder();
        scanner = builder.getScanner();
        passengers = builder.getPassengers();
    }

    @TestFactory // Test 1.1
    public Stream<DynamicTest> testPassengersInitialized() {
        List<String> expectedNames = Arrays.asList(
                "Faraz Abbasi",
                "Paul Adams",
                "Graeme Alderson",
                "Adnan Ali",
                "Wajid Ali",
                "Edward Allnutt",
                "Oscar Alonso",
                "John Andrews",
                "Nicole Antoniou",
                "Anne Arbuckle",
                "Hellen Armstrong",
                "Phillip Arrowsmith",
                "Jim Ashcroft",
                "Robert Ashworth",
                "Margaret Axford",
                "Ashraf Aziz",
                "Rhoda Badger",
                "Peter Badley",
                "Paul Bailey",
                "David Bailey",
                "Jonathon Baker",
                "Kevin Bales",
                "Norman Banner",
                "Abdul Bari",
                "Clyde Barrow",
                "Celia Bartlett",
                "Charles Barwell",
                "Martin Beard",
                "Antonie Beck",
                "Linda Beckett",
                "Christopher Bell",
                "Raymond Bell",
                "Paul Bennett",
                "Simon Bennett",
                "Janet Benson",
                "Helen Betteridge",
                "Shahid Bhatti",
                "David Biggins",
                "Amanda Birchall",
                "Simon Bird",
                "Graham Birdsall",
                "Philip Bish",
                "Elizabeth Bishop",
                "Michael Bishop",
                "Brian Black",
                "Ian Black",
                "Jill Blane",
                "Melanie Bliss",
                "Robin Boardman",
                "Clare Bolton",
                "Loretta Bonfield",
                "Terence Boon",
                "Andrew Boorman",
                "Philip Bottomley",
                "Gavin Bottomley",
                "Richard Bower",
                "Richard Bradley",
                "Dominic Brady",
                "Patricia Brennan",
                "Martin Brennan",
                "John Bridges",
                "Cherry Brogan",
                "Paul Bromley",
                "Nicholas Broom",
                "Christopher Brown",
                "Alexander Brown",
                "Michael Brown",
                "Sharon Buchanan",
                "Carmel Buckley",
                "Jill Buckley",
                "Shirley Bullard",
                "Claire Bunnell",
                "Louise Burns",
                "Anthony Burrell",
                "Moira Burrow",
                "Elizabeth Caines",
                "Anne Campbell",
                "Patrick Campbell",
                "Keith Carmichael",
                "Janice Carnegie",
                "Brian Carroll",
                "Janice Carter",
                "Douglas Cassidy",
                "Jacqueline Chandler",
                "Kirsten Chaney",
                "Richard Chilton",
                "Dorothy Chin",
                "Sukofv Chopra",
                "William Clapham",
                "Elizabeth Clark",
                "Ryan Clarke",
                "Stuart Clements",
                "Ronald Cobb",
                "Joseph Cobb",
                "Jonathan Coe",
                "Margery Collins",
                "Russell Collins",
                "Ian Connaughton",
                "Elizabeth Connors",
                "Amanda Cook",
                "Ian Cooke",
                "Benjamin Coomber",
                "Charlotte Coombes",
                "Mervyn Cooper",
                "Carol Cooper",
                "Gerald Cooper",
                "William Cosgrove",
                "Marco Costa",
                "Eileen Cox",
                "David Cross",
                "Jonathan Cummins",
                "Elizabeth Cunningham",
                "Cindy Dally",
                "Steven Darby",
                "Gail Dart",
                "Mohsin Darwish",
                "Alexander Davey",
                "Frances Davey",
                "Iain Davidson",
                "Frank Davies",
                "Paul Davies",
                "Mary Davies",
                "Thomas Dawkins",
                "Paul Day",
                "Brian Deakin",
                "Thomas Deakin",
                "Emma Dean",
                "Jonthan Dean",
                "Ian Delph",
                "Mark Dent",
                "James Dent",
                "Gidon Deutsch",
                "Ingrid Devane",
                "William Dickson",
                "Graeme Dickson",
                "Robert Dickson",
                "Dina Dodds",
                "John Doherty",
                "Linda Doney",
                "Peter Donnelly",
                "Gary Donoghue",
                "Miles Drew",
                "John Duffy",
                "Marian Duggan",
                "Brendan Duke",
                "Shakil Durrani",
                "Jonathan Earles",
                "Dale Easdon",
                "Robert Edge",
                "Joanne Edwards",
                "Lisa Edwards",
                "Joseph Elias",
                "Eileen Elsey",
                "Alan Elwood",
                "Carol Emerson",
                "Anthony Emery",
                "Alan England",
                "John Enoch",
                "Clare Evans",
                "James Fairley",
                "Alan Falconer",
                "Karen Falconer",
                "David Farnworth",
                "Robert Farquharson",
                "Lester Farrelly",
                "Matthew Farrow",
                "Roland Faulkner",
                "David Fenn",
                "John Ferguson",
                "Sarah Ferns",
                "Michael Ferris",
                "Beatrice Fields",
                "Mark Fillingham",
                "Simon Fish",
                "Matthew Fleming",
                "John Fletcher",
                "Michelle Ford",
                "Petrina Forsyth",
                "Fiona Foster",
                "Wendy Fox",
                "Theresa Francis",
                "Marcus Franks",
                "Helen Freeman",
                "Georgina Gallagher",
                "Carol Gamble",
                "Richard Gardiner",
                "Ross Gardner",
                "Paul Garland",
                "David Gaunt",
                "Christiana Georgeson",
                "Colum Geraghty",
                "James Gibb",
                "Dean Gibson",
                "Adrian Gilmour",
                "Liam Glasper",
                "Andres Gomez",
                "John Goodyear",
                "Peter Gough",
                "David Graham",
                "Steven Graham",
                "Aimee Greaves",
                "Kristian Green",
                "Martin Green",
                "Christian Green",
                "Julie Green",
                "Debbie Griffin",
                "James Grundy",
                "Christopher Guy",
                "Peter Haase",
                "Carol Hamilton",
                "Sylvia Hampson",
                "Garry Hancock",
                "Liam Hands",
                "Peter Hannam",
                "Ernest Hansen",
                "John Hardcastle",
                "Steven Harding",
                "Gabrielle Harding",
                "David Hards",
                "Wendy Hardwick",
                "Jack Hare",
                "Nigel Harley",
                "Russell Harman",
                "Antony Harmston",
                "Caroline Harper",
                "Paul Harriman",
                "Paul Harrington",
                "Judith Harris",
                "Peter Harris",
                "Louise Harris",
                "Brian Harrison",
                "Gillian Harrison",
                "Barbara Harrison",
                "Mavis Harrison",
                "Samuel Harrison",
                "Gloria Hart",
                "Jennifer Hartnell",
                "Patricia Harvey",
                "Reginald Harwood",
                "Khalida Hasan",
                "Hasan Hassan",
                "Tm Hawkins",
                "Darren Hayes",
                "Mark Hayes",
                "Carla Heath",
                "Kamaljeet Heer",
                "James Hemphill",
                "Mary Henderson",
                "James Henshaw",
                "Valerie Hepworth",
                "Neil Heywood",
                "Glynis Hicken",
                "Peter Higgs",
                "Sheelagh Hill",
                "Daniel Hillis",
                "Ann Hilton",
                "Nicola Hird",
                "Brian Hodge",
                "Lyn Hodges",
                "Sarah Hodgkins",
                "Sarah Hodgson",
                "Christine Holgate",
                "Bryan Holloran",
                "Chris Holmes",
                "John Holt",
                "Alan Hook",
                "Andrew Hooton",
                "Alan Horan",
                "Terence Horton",
                "Diane Houghton",
                "Alan Howson",
                "Jan Hughes",
                "Joel Hughes",
                "Christine Hughes",
                "Kerry Hunn",
                "Barbara Hunter",
                "Wendy Ing",
                "Janet Ingram",
                "Alan Ives",
                "Paul Jackson",
                "Evelyn Jackson",
                "Andrew Jackson",
                "Geoffrey James",
                "Guy James",
                "David Janson",
                "Stuart Jeffrey",
                "Andrew Jobson",
                "Philomena Johnson",
                "Ronald Jones",
                "Eliane Jones",
                "Keith Jones",
                "Alun Jones",
                "Maxine Jordan",
                "Nicky Joseph",
                "Sailesh Joshi",
                "Ranjit Kaur",
                "Joanne Kavanagh",
                "Gordon Kearney",
                "Christine Kearns",
                "James Keegan",
                "Sarah Keith",
                "Gordon Kelly",
                "Mary Kelly",
                "James Kelly",
                "Graham Kemp",
                "David Kemp",
                "Nina Kemp",
                "Tracey Kemp",
                "Paul Kenworthy",
                "Catherine Kerr",
                "Jacqueline Kerslake",
                "Sarah Khan",
                "Shahzad Khan",
                "Mary Knight",
                "James Knight",
                "Ben Knill",
                "Doris Knowles",
                "Elaine Lavender",
                "Paul Laws",
                "Edlend Lee",
                "Lynda Lee",
                "Philip Leese",
                "Heather Legard",
                "Alison Lenton",
                "Sheila Lewis",
                "David Lewis",
                "Kathleen Leyland",
                "Samuel Little",
                "Timothy Llewellyn",
                "Stephanie Locke",
                "Victoria Long",
                "Joaquin Loera",
                "Fiona Low",
                "Cara Luck",
                "Gavin Luxton",
                "Andrew Lydon",
                "James Lynn",
                "Mark MacArthur",
                "David MacKay",
                "George MacKay",
                "Kevin MacKenzie",
                "Jaqueline Magee",
                "Valerie Maguire",
                "Donna Maher",
                "Marie Main",
                "Julian Mair",
                "Marcin Majewski",
                "Tony Mak",
                "Saeed Malik",
                "Paul Malone",
                "David Mann",
                "Brian Mansfield",
                "Shaun Mansfield",
                "Anthony Marchese",
                "Gary Marland",
                "Ian Marlow",
                "Christine Martin",
                "Keith Martin",
                "Julian Mayne",
                "Gerald McAfee",
                "James McAlister",
                "Rachel McClurg",
                "Andrew McDonald",
                "Christine McEwan",
                "Darren McIndoe",
                "Jennifer McLaughlin",
                "Emma Melrose",
                "Sarah Mendez",
                "Maria Millan",
                "Keith Miller",
                "Richard Mills",
                "Alistair Milner",
                "Brian Minns",
                "Gary Mitchell",
                "Sylvia Mogan",
                "Hadia Mohammed",
                "Carolyn Moir",
                "Sean Monaghan",
                "David Monson",
                "Charles Moore",
                "Nathan Moore",
                "Marguerite Moore",
                "Thomas Morgan",
                "David Morgan",
                "Gary Moriarty",
                "Julian Morison",
                "Kenneth Morris",
                "Bernard Moss",
                "Carol Muir",
                "Martin Mullen",
                "Paul Mullin",
                "Roy Murrell",
                "Nicolas Myers",
                "Irene Nash",
                "Malka Nathan",
                "Grahame Neve",
                "Simon Newell",
                "Wayne Newell",
                "Irene Nicholls",
                "Richard Nicholson",
                "Margaret Nicol",
                "Rosemond Nicol",
                "Deborah Nightingale",
                "John Nihill",
                "John Noble",
                "Stephen Nock",
                "Mark Noonan",
                "Abdul Noor",
                "Karen Norman",
                "Derry North",
                "Mary Nunn",
                "Frank Ogg",
                "Susan Ord",
                "Farah Osman",
                "Sharon Owen",
                "David Owen",
                "Robert Palmer",
                "Christopher Parish",
                "Bonnie Parker",
                "Penelope Parr",
                "Ross Parr",
                "Michael Parry",
                "Sarju Patel",
                "Sushila Patel",
                "Nisa Patel",
                "Tarlek Patel",
                "Robert Payne",
                "Simon Pearson",
                "Peter Penfold",
                "Lisa Peters",
                "Eden Phillips",
                "Jane Pick",
                "Elaine Pickard",
                "Jeff Pitt",
                "Andrew Pockett",
                "Robert Pollitt",
                "Beverly Poole",
                "Michael Porter",
                "David Posen",
                "Stephen Poulter",
                "Krishna Prasad",
                "Raymond Pratley",
                "Mark Prescott",
                "Brenda Proctor",
                "Maxwell Pugsley",
                "Linda Rae",
                "Gail Rance",
                "Ann Rattray",
                "Ian Raven",
                "Graham Raynor",
                "Rupert Read",
                "Dean Redmond",
                "Sarah Reed",
                "David Reeve",
                "Louise Rennie",
                "Barry Richards",
                "Guy Richardson",
                "Inez Richardson",
                "Martyn Riley",
                "John Roberts",
                "Jill Roberts",
                "Mark Robinson",
                "Simon Rogers",
                "Roberto Romanello",
                "Carol Rookwood",
                "Joao Rosa",
                "Pauline Ross",
                "Karen Rourke",
                "George Routledge",
                "Rita Rowe",
                "Jan Rozanski",
                "Neil Rundle",
                "Audrey Rundle",
                "James Russell",
                "Peter Russell",
                "Timothy Russell",
                "Stephanie Ryan",
                "Arnol Salas",
                "Emma Sargent",
                "John Scott",
                "Matthew Scott",
                "Alfred Sena",
                "Jonathan Severn",
                "Dhimant Shah",
                "April Shaikh",
                "Johanna Sharp",
                "Jonathan Sharp",
                "Steven Sharpe",
                "John Sharpe",
                "Carol Shaw",
                "Azhar Sheikh",
                "Nicola Shepard",
                "John Sheppard",
                "Peter Sherry",
                "Peter Shipman",
                "Ian Short",
                "Salinder Sidhu",
                "Perminder Sidhu",
                "Alexandra Sigley",
                "Tony Skerrett",
                "Michael Slattery",
                "Jane Slaughter",
                "Richard Sloggett",
                "Lee Smart",
                "Bridget Smith",
                "Robin Smith",
                "Janice Smith",
                "Kenneth Soper",
                "Tracy Stark",
                "Daniel Steel",
                "Shane Steer",
                "Pamela Stephenson",
                "Christopher Stevens",
                "Kenneth Stirling",
                "Jeanette Stoker",
                "Rebecca Stokes",
                "Mike Stoodley",
                "Jayne Strange",
                "Jonathan Stubbs",
                "Sindra Sultan",
                "Reema Tahir",
                "Eric Tan",
                "Russell Tandy",
                "David Tate",
                "Timothy Taylor",
                "John Telfer",
                "Emma Thomas",
                "Valerie Thompson",
                "Barry Thompson",
                "Spencer Thomson",
                "Lyndsay Thomson",
                "Jennifer Timlin",
                "Ian Tipton",
                "Patrick Tobin",
                "Nicola Tonks",
                "Patricia Townsend",
                "Thomas Trimby",
                "Adrian Trimmings",
                "Eduard Turk",
                "Steven Turner",
                "Brian Usher",
                "Alan White",
                "Lisa White",
                "Stephen Whiteley",
                "Heidi Whitfield",
                "Evelyne Whitley",
                "Elizabeth Whyte",
                "Stefan Wickham",
                "Denise Wildman",
                "Karen Wilkinson",
                "Simon Williams",
                "Joanna Williams",
                "John Williams",
                "Thomas Williams",
                "Austin Wilson",
                "Christine Wilson",
                "Cliff Wilson",
                "Paul Wilson",
                "Harvey Wilson",
                "Anne Wood",
                "Nathalie Wood",
                "Nick Worrall",
                "David Wright",
                "Joseph Wright",
                "Anita Wright",
                "Jessie Wylie",
                "Amber Wynne",
                "Richard Young"
        );


        return IntStream.range(0, expectedNames.size())
                .mapToObj(index -> DynamicTest.dynamicTest(
                        "should have read " + expectedNames.get(index),
                        () -> assertEquals(passengers.get(index).getName(), expectedNames.get(index))
                ));
    }

    @Test //Test 1.2
    public void testBaggagesInitialized() {
        int baggageLen = 0;
        for (Passenger passenger : passengers) {
            baggageLen += passenger.getHandBaggages().length;
        }

        assertEquals(baggageLen, 609);
    }


    @Test //Test 2
    public void testEmployeesCorrectlySet() {
        FederalPoliceOfficer o1 = scanner.getO1();
        assertNotNull(o1);
        assertEquals(o1.getName(), "Wesley Snipes");
        assertNull(scanner.getO2());
        assertNull(scanner.getO3());

        InspectorRollerConveyor i1 = scanner.getRollerConveyor().getInspector();
        InspectorOperationStation i2 = scanner.getOperatingStation().getInspector();
        InspectorManualPostControl i3 = scanner.getManualPostControl().getInspector();
        assertNotNull(i1);
        assertEquals(i1.getName(), "Clint Eastwood");
        assertNotNull(i2);
        assertEquals(i2.getName(), "Natalie Portman");
        assertNotNull(i3);
        assertEquals(i3.getName(), "Bruce Willis");

        Supervisor supervisor = scanner.getSupervision().getSupervisor();
        assertNotNull(supervisor);
        assertEquals(supervisor.getName(), "Jodie Foster");
    }

    @Test //Test 3
    public void LockIDCard() {
        IDCardReader idCardReader = new IDCardReader();
        IDCard idCard = new IDCard(IDCardType.STAFF, ProfileType.I, "Gustav1939");
        idCardReader.readCard(idCard);
        idCardReader.validatePIN("Gustav1938");
        idCardReader.validatePIN("Gustav1937");
        idCardReader.validatePIN("Gustav1940");
        assertTrue(idCard.isLocked());
    }

    @Test //Test 4
    public void AuthorizeProfileKandO() {
        IDCardReader idCardReader = new IDCardReader();

        IDCard idCardK = new IDCard(IDCardType.EXTERNAL, ProfileType.K, "Peter1995");
        assertFalse(idCardReader.readCard(idCardK));

        IDCard idCardO = new IDCard(IDCardType.EXTERNAL, ProfileType.O, "Michael1993");
        assertFalse(idCardReader.readCard(idCardO));
    }

    @Test //Test 5
    public void OnlyAllowCertainFunctions() {

        //Throws Exception, if Instructor wants to unlock Baggage-Scanner
        lockScannerHelper();
        assertThrows(WrongProfileTypeException.class, () -> {
           scanner.unlock(ProfileType.I);
        });

        //Throws NO Exception, if Supervisor wants to unlock Baggage-Scanner
        assertDoesNotThrow(() ->{
            scanner.unlock(ProfileType.S);
        });

        //Throws Exception, if Supervision wants to move Belt
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.moveBeltBackwards(ProfileType.S);
        });
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.moveBeltForward(ProfileType.S);
        });

        //Throws NO Exception, if Inspector wants to move Belt
        assertDoesNotThrow( () -> {
            scanner.moveBeltBackwards(ProfileType.I);
        });
        assertDoesNotThrow(() -> {
            scanner.moveBeltForward(ProfileType.I);
        });

        //Throws Exception, if Supervision wants to scan
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.scan(ProfileType.S);
        });

        //Throws NO Exception, if Instructor wants to scan
        HandBaggage[] handBaggages = passengers.get(0).getHandBaggages();
        Belt belt = scanner.getBelt();
        Tray tray = new Tray();
        tray.setBaggage(handBaggages[0]);
        belt.setEntrance(tray);
        assertDoesNotThrow(() -> {
            scanner.scan(ProfileType.I);
        });

        //Throws Exception, if Supervision wants to alarm
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.alarm(ProfileType.S);
        });

        //Throws NO Exception, if Instructor wants to alarm
        assertDoesNotThrow(() -> {
            scanner.alarm(ProfileType.I);
        });

        //Throws Exception, if Supervision wants to activate
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.activate(ProfileType.S);
        });

        //Throws NO Exception, if Instructor wants to activate
        assertDoesNotThrow(() -> {
            scanner.activate(ProfileType.I);
        });

        //Throws Exception, if Instructor wants to report
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.report(ProfileType.I);
        } );

        //Throws NO Exception, if Supervisor wants to report
        assertDoesNotThrow(() -> {
            scanner.report(ProfileType.S);
        });





    }

    @Test //Test 6
    public void OnlySupervisorCanUnlock() {
        lockScannerHelper();
        assertDoesNotThrow( () -> {
                scanner.unlock(ProfileType.S);
        });
        assertEquals(BaggageScannerState.ACTIVATED, scanner.getState());

        lockScannerHelper();
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.unlock(ProfileType.I);
        });
        assertNotEquals(BaggageScannerState.ACTIVATED, scanner.getState());

        lockScannerHelper();
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.unlock(ProfileType.O);
        });
        assertNotEquals(BaggageScannerState.ACTIVATED, scanner.getState());

        lockScannerHelper();
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.unlock(ProfileType.T);
        });
        assertNotEquals(BaggageScannerState.ACTIVATED, scanner.getState());

        lockScannerHelper();
        assertThrows(WrongProfileTypeException.class, () -> {
            scanner.unlock(ProfileType.K);
        });
        assertNotEquals(BaggageScannerState.ACTIVATED, scanner.getState());
    }

    public void lockScannerHelper(){
        scanner.alarm(ProfileType.I);
        assertEquals(BaggageScannerState.LOCKED, scanner.getState());
    }

    @Test //Test 7
    public void ScanFindsKnife() {
        // Test for knife: Oscar Alonso;1;[K,1,3]
        Passenger passenger = passengers.get(6);
        Record record = getRecord(passenger, 0);
        assertTrue(record.getResult().startsWith("prohibited item | knife detected at position "));
    }

    @Test //Test 8
    public void ScanFindsWeapon() {
        // Test for weapon: Margaret Axford;3;[W,2,5]
        Passenger passenger = passengers.get(14);
        Record record = getRecord(passenger, 1);
        assertTrue(record.getResult().startsWith("prohibited item | weapon - glock7 detected at position "));
    }

    @Test //Test 9
    public void ScanFindsExplosive() {
        // Test for explosive: Joaquin Loera;1;[E,1,1]
        Passenger passenger = passengers.get(331);
        Record record = getRecord(passenger, 0);
        assertTrue(record.getResult().startsWith("prohibited item | explosive detected at position "));
    }

    @Test //Test for Clean (Not specifically asked for)
    public void ScanRecognisesClean() {
        // Test for clean: Faraz Abbasi;1;-
        Passenger passenger = passengers.get(0);
        Record record = getRecord(passenger, 0);
        assertEquals(record.getResult(), "clean");
    }

    public Record getRecord(Passenger passenger, int whichBaggage) {
        HandBaggage baggage = passenger.getHandBaggages()[whichBaggage];
        return scanner.scan(baggage);
    }

    @TestFactory //Test 10
    public Stream<DynamicTest> DocumentationOfScan() {

        List<HandBaggage> combinedBaggages = new ArrayList<>();

        for (Passenger passenger : passengers) {
            combinedBaggages.addAll(Arrays.asList(passenger.getHandBaggages()));
        }

        return combinedBaggages.stream()
                .map(handBaggage -> DynamicTest.dynamicTest(
                        "BaggageScanner should store Record after scanning baggage " + combinedBaggages.indexOf(handBaggage),
                        () -> {
                            Record record = scanner.scan(handBaggage);
                            assertTrue(scanner.getRecords().contains(record));
                        }
                ));
    }

}