import java.util.Scanner;
import java.io.*;

class Student {
	private int ID;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;

	public Student() {
		ID = 1000; // never used but offset to eliminate problems
		firstName = "";
		lastName = "";
		address = "";
		city = "";
		state = "";
	}

	public Student(String firstName, String lastName, String address, String city, String state) {// to create object
		this.ID = 1000; //never used but offset to eliminate problems
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public Student(int ID, String firstName, String lastName, String address, String city, String state) {// to set correct ID number																											// reconstruct																											// object
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public int getID() {
		return this.ID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void printStudent() {
		System.out.println("Student ID: " + this.ID);
		System.out.println("First: " + this.firstName);
		System.out.println("Last: " + this.lastName);
		System.out.println("Address: " + this.address);
		System.out.println("City: " + this.city);
		System.out.println("State: " + this.state);
	}
}

class StudentFile {
	private final int RECORD_SIZE = 168; // ID = 4, firstName = 30, lastName = 40, address = 60, city = 30, state = 4
	private RandomAccessFile studentFile;

	public StudentFile(String filename) throws FileNotFoundException {
		try {
			studentFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing student file " + filename);
		}
	}

	public void writeStudentFile(Student temp) throws IOException {
		studentFile.writeInt(temp.getID()); // write the ID (4 bytes)
		String firstName = temp.getFirstName();
		if (firstName.length() > 15) { // write the first name (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				studentFile.writeChar(firstName.charAt(i));
		} else {
			studentFile.writeChars(firstName);
			for (int i = 0; i < 15 - firstName.length(); i++)
				studentFile.writeChar(' ');
		}

		String lastName = temp.getLastName();
		if (lastName.length() > 20) { // write the last name , (20 chars, 40 bytes)
			for (int i = 0; i < 20; i++)
				studentFile.writeChar(lastName.charAt(i));
		} else {
			studentFile.writeChars(lastName);
			for (int i = 0; i < 20 - lastName.length(); i++)
				studentFile.writeChar(' ');
		}

		String address = temp.getAddress();
		if (address.length() > 30) { // write the address, (30 chars, 60 bytes)
			for (int i = 0; i < 30; i++)
				studentFile.writeChar(address.charAt(i));
		} else {
			studentFile.writeChars(address);
			for (int i = 0; i < 30 - address.length(); i++)
				studentFile.writeChar(' ');
		}

		String city = temp.getCity();
		if (city.length() > 15) { // write the city, (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				studentFile.writeChar(city.charAt(i));
		} else {
			studentFile.writeChars(city);
			for (int i = 0; i < 15 - city.length(); i++)
				studentFile.writeChar(' ');
		}

		String state = temp.getState();
		if (state.length() > 2) { // write the state, (2 chars, 4 bytes)
			for (int i = 0; i < 2; i++)
				studentFile.writeChar(state.charAt(i));
		} else {
			studentFile.writeChars(state);
			for (int i = 0; i < 2 - state.length(); i++)
				studentFile.writeChar(' ');
		}
	}

	public Student readStudentFile() throws IOException {
		int ID = studentFile.readInt(); // extract ID

		char[] firstNameCharArray = new char[15]; // extract firstName
		for (int i = 0; i < 15; i++)
			firstNameCharArray[i] = studentFile.readChar();
		String firstName = new String(firstNameCharArray);
		firstName.trim();

		char[] lastNameCharArray = new char[20]; // extract lastName
		for (int i = 0; i < 20; i++)
			lastNameCharArray[i] = studentFile.readChar();
		String lastName = new String(lastNameCharArray);
		lastName.trim();

		char[] addressCharArray = new char[30]; // extract address
		for (int i = 0; i < 30; i++)
			addressCharArray[i] = studentFile.readChar();
		String address = new String(addressCharArray);
		address.trim();

		char[] cityCharArray = new char[15]; // extract city
		for (int i = 0; i < 15; i++)
			cityCharArray[i] = studentFile.readChar();
		String city = new String(cityCharArray);
		city.trim();

		char[] stateCharArray = new char[2]; // extract state
		for (int i = 0; i < 2; i++)
			stateCharArray[i] = studentFile.readChar();
		String state = new String(stateCharArray);
		state.trim();

		Student temp = new Student(ID, firstName, lastName, address, city, state);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		studentFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return studentFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		studentFile.close();
	}
}

class Course {
	private int CNUM;
	private String CID;
	private String cName;
	private String instructor;
	private String department;

	public Course() {
		this.CNUM = 1000;  //never used but offset to eliminate possible issues
		this.CID = "";
		this.cName = "";
		this.instructor = "";
		this.department = "";
	}

	public Course(String CID, String cName, String instructor, String department) { // to create object
		this.CNUM = 1000; //never used but offset to eliminate potential problems
		this.CID = CID;
		this.cName = cName;
		this.instructor = instructor;
		this.department = department;
	}

	public Course(int CNUM, String CID, String cName, String instructor, String department) { // to reconstruct objet
		this.CNUM = CNUM;
		this.CID = CID;
		this.cName = cName;
		this.instructor = instructor;
		this.department = department;
	}

	public int getCNUM() {
		return this.CNUM;
	}

	public String getCID() {
		return this.CID;
	}

	public String getcName() {
		return this.cName;
	}

	public String getInstructor() {
		return this.instructor;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setCID(String CID) {
		this.CID = CID;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void printCourse() {
		System.out.println("CNUM: " + this.CNUM);
		System.out.println("CID: " + this.CID);
		System.out.println("Course Name: " + this.cName);
		System.out.println("Instructor: " + this.instructor);
		System.out.println("Department: " + this.department);
	}
}

class CourseFile {
	private final int RECORD_SIZE = 110; // CNUM = 4, CID = 12, cName = 24, instructor = 30, department = 40
	private RandomAccessFile courseFile;

	public CourseFile(String filename) throws FileNotFoundException {
		try {
			courseFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing course file " + filename);
		}
	}

	public void writeCourseFile(Course temp) throws IOException {
		courseFile.writeInt(temp.getCNUM()); // write the CNUM (4 bytes)
		String CID = temp.getCID();
		if (CID.length() > 6) { // write the Course ID (6 chars, 12 bytes)
			for (int i = 0; i < 6; i++)
				courseFile.writeChar(CID.charAt(i));
		} else {
			courseFile.writeChars(CID);
			for (int i = 0; i < 6 - CID.length(); i++)
				courseFile.writeChar(' ');
		}

		String cName = temp.getcName();
		if (cName.length() > 12) { // write the course name , (12 chars, 24 bytes)
			for (int i = 0; i < 12; i++)
				courseFile.writeChar(cName.charAt(i));
		} else {
			courseFile.writeChars(cName);
			for (int i = 0; i < 12 - cName.length(); i++)
				courseFile.writeChar(' ');
		}

		String instructor = temp.getInstructor();
		if (instructor.length() > 15) { // write the instructor, (15 chars, 30 bytes)
			for (int i = 0; i < 15; i++)
				courseFile.writeChar(instructor.charAt(i));
		} else {
			courseFile.writeChars(instructor);
			for (int i = 0; i < 15 - instructor.length(); i++)
				courseFile.writeChar(' ');
		}

		String department = temp.getDepartment();
		if (department.length() > 20) { // write the department, (20 chars, 40 bytes)
			for (int i = 0; i < 20; i++)
				courseFile.writeChar(department.charAt(i));
		} else {
			courseFile.writeChars(department);
			for (int i = 0; i < 20 - department.length(); i++)
				courseFile.writeChar(' ');
		}

	}

	public Course readCourseFile() throws IOException {
		int CNUM = courseFile.readInt(); // extract CNUM

		char[] CIDCharArray = new char[6]; // extract Course ID
		for (int i = 0; i < 6; i++)
			CIDCharArray[i] = courseFile.readChar();
		String CID = new String(CIDCharArray);
		CID.trim();

		char[] cNameCharArray = new char[12]; // extract Course Name
		for (int i = 0; i < 12; i++)
			cNameCharArray[i] = courseFile.readChar();
		String cName = new String(cNameCharArray);
		cName.trim();

		char[] InstructorCharArray = new char[15]; // extract instructor
		for (int i = 0; i < 15; i++)
			InstructorCharArray[i] = courseFile.readChar();
		String instructor = new String(InstructorCharArray);
		instructor.trim();

		char[] DepartmentCharArray = new char[20]; // extract department
		for (int i = 0; i < 20; i++)
			DepartmentCharArray[i] = courseFile.readChar();
		String department = new String(DepartmentCharArray);
		department.trim();

		Course temp = new Course(CNUM, CID, cName, instructor, department);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		courseFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return courseFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		courseFile.close();
	}

}

class Enrollment {
	//private static int count = 1;
	private int enrollmentID;
	private int studentID;
	private int cNum;
	private String year;
	private String semester;
	private String grade;

	public Enrollment() {
		this.studentID = 0;
		this.cNum = 0;
		this.year = "";
		this.semester = "";
		this.grade = "";
	}

	public Enrollment(int studentID, int cNum, String year, String semester, String grade) {
		this.enrollmentID = 1000; // never used offset to avoid a potential problem
		this.studentID = studentID;
		this.cNum = cNum;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}

	public Enrollment(int enrollmentID, int studentID, int cNum, String year, String semester, String grade) {
		this.enrollmentID = enrollmentID;
		this.studentID = studentID;
		this.cNum = cNum;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}

	public int getStudentID() {
		return this.studentID;
	}

	public int getcNum() {
		return this.cNum;
	}

	public int getEID() {
		return this.enrollmentID;
	}

	public String getYear() {
		return this.year;
	}

	public String getSemester() {
		return this.semester;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public void setcNum(int cNum) {
		this.cNum = cNum;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void printEnrollment() {
		System.out.println("Enrollment ID: " + this.enrollmentID);
		System.out.println("StudentID: " + this.studentID);
		System.out.println("Course Number: " + this.cNum);
		System.out.println("Year: " + this.year);
		System.out.println("Semester: " + this.semester);
		System.out.println("Grade: " + this.grade);
	}

}

class EnrollmentFile {
	private final int RECORD_SIZE = 38; // BYTES Enrollment ID = 4, Student ID = 4, cNum = 4, year = 8, semester = 16,
										// grade = 2
	private RandomAccessFile enrollmentFile;

	public EnrollmentFile(String filename) throws FileNotFoundException {
		try {
			enrollmentFile = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error processing enrollment file " + filename);
		}
	}

	public void writeEnrollmentFile(Enrollment temp) throws IOException {
		enrollmentFile.writeInt(temp.getEID()); // write the enrollment ID (4 bytes)
		enrollmentFile.writeInt(temp.getStudentID()); // write the Student ID (4 bytes)
		enrollmentFile.writeInt(temp.getcNum()); // write the course number (4 bytes)
		String year = temp.getYear();
		if (year.length() > 4) { // write the year (4 chars, 8 bytes)
			for (int i = 0; i < 4; i++)
				enrollmentFile.writeChar(year.charAt(i));
		} else {
			enrollmentFile.writeChars(year);
			for (int i = 0; i < 4 - year.length(); i++)
				enrollmentFile.writeChar(' ');
		}

		String semester = temp.getSemester();
		if (semester.length() > 8) { // write the semester , (8 chars, 16 bytes)
			for (int i = 0; i < 8; i++)
				enrollmentFile.writeChar(semester.charAt(i));
		} else {
			enrollmentFile.writeChars(semester);
			for (int i = 0; i < 8 - semester.length(); i++)
				enrollmentFile.writeChar(' ');
		}

		String grade = temp.getGrade();
		if (grade.length() > 1) { // write the instructor, (15 chars, 30 bytes)
			for (int i = 0; i < 1; i++)
				enrollmentFile.writeChar(grade.charAt(i));
		} else {
			enrollmentFile.writeChars(grade);
			for (int i = 0; i < 1 - grade.length(); i++)
				enrollmentFile.writeChar(' ');
		}

	}

	public Enrollment readEnrollmentFile() throws IOException {
		int enrollmentID = enrollmentFile.readInt(); // extract enrollment ID
		int studentID = enrollmentFile.readInt(); // extract student ID
		int cNum = enrollmentFile.readInt(); // extract course number
		char[] yearCharArray = new char[4]; // extract year field
		for (int i = 0; i < 4; i++)
			yearCharArray[i] = enrollmentFile.readChar();
		String year = new String(yearCharArray);
		year.trim();

		char[] semesterCharArray = new char[8]; // extract semester
		for (int i = 0; i < 8; i++)
			semesterCharArray[i] = enrollmentFile.readChar();
		String semester = new String(semesterCharArray);
		semester.trim();

		char[] gradeCharArray = new char[1]; // extract grade
		for (int i = 0; i < 1; i++)
			gradeCharArray[i] = enrollmentFile.readChar();
		String grade = new String(gradeCharArray);
		grade.trim();

		Enrollment temp = new Enrollment(enrollmentID, studentID, cNum, year, semester, grade);
		return temp;
	}

	private long getByteNum(long recordNum) {
		return RECORD_SIZE * recordNum;
	}

	public void moveFilePointer(long recordNum) throws IOException {
		enrollmentFile.seek(getByteNum(recordNum));
	}

	public long getNumberOfRecords() throws IOException {
		return enrollmentFile.length() / RECORD_SIZE;
	}

	public void close() throws IOException {
		enrollmentFile.close();
	}

	public int readInt() throws IOException {
		return enrollmentFile.readInt();
	}
}

public class UniversityEnrollment {
	static void printMenu() {
		System.out.println("Welcome to University Enrollment");
		System.out.println("1.  Create Student");
		System.out.println("2.  Create Course");
		System.out.println("3.  Create Enrollment");
		System.out.println("4.  Edit Student");
		System.out.println("5.  Edit Course");
		System.out.println("6.  Edit Enrollment");
		System.out.println("7.  Display Student[s]");
		System.out.println("8.  Display Course[s]");
		System.out.println("9.  Display Enrollments[s]");
		System.out.println("10. Grades Sub Menu");
		System.out.println("0.  --- Quit ---");
	}

	static void printSubMenu() {
		System.out.println("Grades Menu");
		System.out.println("1. View Grades by Student");
		System.out.println("2. View Grades by Course");
		System.out.println("3. Edit Grades by Student");
		System.out.println("4. Edit Grades by Course");
		System.out.println("0. --- Exit to Menu ---");
	}

	static Student makeNewStudent(int numberOfStudentsInFile) {
		Scanner keyboard = new Scanner(System.in);
		String firstName;
		String lastName;
		String address;
		String city;
		String state;
		System.out.print("First Name: ");
		firstName = keyboard.nextLine();
		System.out.print("Last Name: ");
		lastName = keyboard.nextLine();
		System.out.print("Address: ");
		address = keyboard.nextLine();
		System.out.print("City: ");
		city = keyboard.nextLine();
		System.out.print("State: ");
		state = keyboard.nextLine();
		Student temp = new Student(numberOfStudentsInFile,firstName, lastName, address, city, state);
		return temp;
	}

	static Course makeNewCourse(int numberOfCoursesInFile) {
		Scanner keyboard = new Scanner(System.in);
		String CID;
		String cName;
		String instructor;
		String department;
		System.out.print("Course ID: ");
		CID = keyboard.nextLine();
		System.out.print("Course Name: ");
		cName = keyboard.nextLine();
		System.out.print("Instructor: ");
		instructor = keyboard.nextLine();
		System.out.print("Department: ");
		department = keyboard.nextLine();
		Course temp = new Course(numberOfCoursesInFile,CID, cName, instructor, department);
		// temp.printCourse();
		return temp;
	}

	static Enrollment makeNewEnrollment(int numberOfEnrollmentsInFile, StudentFile studentRecord, CourseFile courseRecord) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		int studentID;
		int cNum;
		String year;
		String semester;
		String grade;
		System.out.print("Enter student ID: ");
		studentID = keyboard.nextInt();
		while (!verifyStudentID(studentID, studentRecord)) {
			System.out.println("Invalid student ID");
			System.out.print("Enter student ID: ");
			studentID = keyboard.nextInt();
		}
		System.out.print("Enter course ID: ");
		cNum = keyboard.nextInt();
		while (!verifyCourseCNUM(cNum, courseRecord)) {
			System.out.println("Invalid course num");
			System.out.print("Enter course num: ");
			cNum = keyboard.nextInt();
		}
		keyboard.nextLine();
		System.out.print("Enter year: ");
		year = keyboard.nextLine();
		System.out.print("Enter semester: ");
		semester = keyboard.nextLine();
		System.out.print("Enter grade: ");
		grade = keyboard.nextLine();
		Enrollment temp = new Enrollment(numberOfEnrollmentsInFile, studentID, cNum, year, semester, grade);
		// temp.printEnrollment();
		return temp;
	}

	static boolean verifyStudentID(int studentID, StudentFile studentRecord) throws IOException {
		return (studentID > 0 && studentID <= studentRecord.getNumberOfRecords());
	}

	static boolean verifyCourseCNUM(int cNum, CourseFile courseRecord) throws IOException {
		return (cNum > 0 && cNum <= courseRecord.getNumberOfRecords());
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner keyboard = new Scanner(System.in);
		StudentFile studentRecord = new StudentFile("student.dat");
		CourseFile courseRecord = new CourseFile("course.dat");
		EnrollmentFile enrollmentRecord = new EnrollmentFile("enrollment.dat");

		int menuOption = -1;
		do {
			printMenu();
			System.out.print("Please enter a valid choice (1-10, 0 to Quit): ");
			menuOption = keyboard.nextInt();

			if (menuOption == 1) { // Create student
				Long tempValue = new Long(studentRecord.getNumberOfRecords());
				int numberOfStudentsInFile = tempValue.intValue()+1; // To offset index 0
				Student temp = makeNewStudent(numberOfStudentsInFile);
				temp.printStudent();
				studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
				studentRecord.writeStudentFile(temp);

			} else if (menuOption == 2) { // Create Course
				Long tempValue = new Long(courseRecord.getNumberOfRecords());
				int numberOfCoursesInFile = tempValue.intValue()+1; // To offset index 0
				Course temp = makeNewCourse(numberOfCoursesInFile);
				temp.printCourse();
				courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
				courseRecord.writeCourseFile(temp);
			} else if (menuOption == 3) { // Create Enrollment
				if (studentRecord.getNumberOfRecords() == 0 && courseRecord.getNumberOfRecords() == 0) {
					System.out.println("There are no student or course records - cannot continue");
					continue;
				} else if (courseRecord.getNumberOfRecords() == 0) {
					System.out.println("There are no course records - cannot continue");
					continue;
				} else if (studentRecord.getNumberOfRecords() == 0) {
					System.out.println("There are no student records - cannot continue");
					continue;
				} else {
					Long tempValue = new Long(enrollmentRecord.getNumberOfRecords());
					int numberOfEnrollmentsInFile = tempValue.intValue()+1; // To offset index 0
					Enrollment temp = makeNewEnrollment(numberOfEnrollmentsInFile,studentRecord, courseRecord);
					temp.printEnrollment();
					enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
					enrollmentRecord.writeEnrollmentFile(temp);
				}

			} else if (menuOption == 4) { // Edit Student
				if (studentRecord.getNumberOfRecords() == 0) {
					System.out.println("[No student records found to edit]");
					continue;
				} else {
					int number = -1;
					while (number < 1 || number > (studentRecord.getNumberOfRecords())) {
						System.out.print(
								"Enter student record # out of " + studentRecord.getNumberOfRecords() + " to edit: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					studentRecord.moveFilePointer(number);
					Student temp = studentRecord.readStudentFile();
					System.out.print("[OLD first name]: " + temp.getFirstName().trim() + " ---> [NEW first name]: ");
					String newFirstName = keyboard.nextLine();
					newFirstName.trim();
					System.out.print("[OLD last name]: " + temp.getLastName().trim() + " - [NEW] last name: ");
					String newLastName = keyboard.nextLine();
					newLastName.trim();
					System.out.print("[OLD] address: " + temp.getAddress().trim() + " - [NEW] address: ");
					String newAddress = keyboard.nextLine();
					newAddress.trim();
					System.out.print("[OLD] city: " + temp.getCity().trim() + " - [NEW] city: ");
					String newCity = keyboard.nextLine();
					newCity.trim();
					System.out.print("[OLD] state: " + temp.getState().trim() + " - [NEW] state: ");
					String newState = keyboard.nextLine();
					newState.trim();
					temp.setFirstName(newFirstName);
					temp.setLastName(newLastName);
					temp.setAddress(newAddress);
					temp.setCity(newCity);
					temp.setState(newState);
					temp.printStudent();
					studentRecord.moveFilePointer(number);
					studentRecord.writeStudentFile(temp);
					studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
				}
			} else if (menuOption == 5) {// Edit Course
				if (courseRecord.getNumberOfRecords() == 0) {
					System.out.println("[No course records found to edit]");
					continue;
				} else {
					int number = -1;
					while (number < 1 || number > (courseRecord.getNumberOfRecords())) {
						System.out.print(
								"Enter course record # out of " + courseRecord.getNumberOfRecords() + " to edit: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					courseRecord.moveFilePointer(number);
					Course temp = courseRecord.readCourseFile();
					System.out.print("[OLD Course ID]: " + temp.getCID().trim() + " ---> [NEW Course ID]: ");
					String newCID = keyboard.nextLine();
					newCID.trim();
					System.out.print("[OLD Course Name]: " + temp.getcName().trim() + " - [NEW] Course Name: ");
					String newcName = keyboard.nextLine();
					newcName.trim();
					System.out.print("[OLD] Instructor: " + temp.getInstructor().trim() + " - [NEW] Instructor: ");
					String newInstructor = keyboard.nextLine();
					newInstructor.trim();
					System.out.print("[OLD] Department: " + temp.getDepartment().trim() + " - [NEW] Department: ");
					String newDepartment = keyboard.nextLine();
					newDepartment.trim();
					temp.setCID(newCID);
					temp.setcName(newcName);
					temp.setInstructor(newInstructor);
					temp.setDepartment(newDepartment);
					temp.printCourse();
					courseRecord.moveFilePointer(number);
					courseRecord.writeCourseFile(temp);
					courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
				}
			} else if (menuOption == 6) { // Edit Enrollment
				if (enrollmentRecord.getNumberOfRecords() != 0) {
					int number = -1;
					while (number < 1 || number > (enrollmentRecord.getNumberOfRecords())) {
						System.out.print("Enter enrollment record # out of " + enrollmentRecord.getNumberOfRecords()
								+ " to display: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					enrollmentRecord.moveFilePointer(number);
					Enrollment temp = enrollmentRecord.readEnrollmentFile();
					System.out.println("Enrollment Details");
					System.out.println("EID: " + temp.getEID());
					System.out.print("[OLD Student ID]: " + temp.getStudentID() + " ---> [NEW Student ID]: ");
					int newStudentID = keyboard.nextInt();

					while (!verifyStudentID(newStudentID, studentRecord)) {
						System.out.println("Invalid student ID");
						System.out.print("[OLD Student ID]: " + temp.getStudentID() + " ---> [NEW Student ID]: ");
						newStudentID = keyboard.nextInt();
					}

					System.out.print("[OLD Course ID]: " + temp.getcNum() + " ---> [NEW] Course Name: ");
					int newcNum = keyboard.nextInt();

					while (!verifyCourseCNUM(newcNum, courseRecord)) {
						System.out.println("Invalid course num");
						System.out.print("[OLD Course ID]: " + temp.getcNum() + " ---> [NEW] Course ID: ");
						newcNum = keyboard.nextInt();
					}

					keyboard.nextLine();
					System.out.print("[OLD] Year: " + temp.getYear().trim() + " ---> [NEW] Year: ");
					String newYear = keyboard.nextLine();
					newYear.trim();
					System.out.print("[OLD] Semester: " + temp.getSemester().trim() + " ---> [NEW] Semester: ");
					String newSemester = keyboard.nextLine();
					newSemester.trim();
					temp.setStudentID(newStudentID);
					temp.setcNum(newcNum);
					temp.setYear(newYear);
					temp.setSemester(newSemester);
					temp.printEnrollment();
					enrollmentRecord.moveFilePointer(number);
					enrollmentRecord.writeEnrollmentFile(temp);
					enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
				} else
					System.out.println("No enrollment records found");

			} else if (menuOption == 7) { // Display Enrollment
				if (studentRecord.getNumberOfRecords() != 0) {
					int number = -1;
					while (number < 1 || number > studentRecord.getNumberOfRecords()) {
						System.out.print("Enter student record # out of " + studentRecord.getNumberOfRecords()
								+ " to display: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					studentRecord.moveFilePointer(number);
					Student temp = studentRecord.readStudentFile();
					temp.printStudent();
					studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
				} else {
					System.out.println("No student records found");
				}
			} else if (menuOption == 8) { // Display Course
				if (courseRecord.getNumberOfRecords() != 0) {
					int number = -1;
					while (number < 1 || number > (courseRecord.getNumberOfRecords())) {
						System.out.print(
								"Enter course record # out of " + courseRecord.getNumberOfRecords() + " to display: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					courseRecord.moveFilePointer(number);
					Course temp = courseRecord.readCourseFile();
					temp.printCourse();
					courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
				} else
					System.out.println("No course records found");
			} else if (menuOption == 9) { // Display Enrollment
				if (enrollmentRecord.getNumberOfRecords() != 0) {
					int number = -1;
					while (number < 1 || number > (enrollmentRecord.getNumberOfRecords())) {
						System.out.print(
								"Enter record # out of " + enrollmentRecord.getNumberOfRecords() + " to display: ");
						number = keyboard.nextInt();
						keyboard.nextLine();
					}
					number = number - 1;
					enrollmentRecord.moveFilePointer(number);
					Enrollment temp = enrollmentRecord.readEnrollmentFile();

					System.out.println("--- Full Enrollment Record ---");
					int extractedStudentID = temp.getStudentID() - 1;
					int extractedcNum = temp.getcNum() - 1;
					studentRecord.moveFilePointer(extractedStudentID);
					Student tempStudentExtract = studentRecord.readStudentFile();
					tempStudentExtract.printStudent();
					studentRecord.moveFilePointer(studentRecord.getNumberOfRecords());
					courseRecord.moveFilePointer(extractedcNum);
					Course tempCourseExtract = courseRecord.readCourseFile();
					tempCourseExtract.printCourse();
					courseRecord.moveFilePointer(courseRecord.getNumberOfRecords());
					temp.printEnrollment();
					enrollmentRecord.moveFilePointer(enrollmentRecord.getNumberOfRecords());
					System.out.println("--- --- --- ---- ---");
				} else
					System.out.println("No enrollment records found");
			} else if (menuOption == 10) { // Sub Menu
				if (enrollmentRecord.getNumberOfRecords() != 0) {
					int subOption = -1;
					do {
						printSubMenu();
						System.out.print("Please enter a valid choice(1-4, 0 to Exit):");
						subOption = keyboard.nextInt();
						
						if (subOption == 1) { // Grades by Student
							System.out.print("Please enter student ID to search: ");
							int studentIDSubMenu = keyboard.nextInt();
							while (!verifyStudentID(studentIDSubMenu, studentRecord)) {
								System.out.println("Invalid student ID");
								System.out.print("Please enter student ID to search: ");
								studentIDSubMenu = keyboard.nextInt(); // adjust to [0] index
							}
							enrollmentRecord.moveFilePointer(0);
							int readValue;

							for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
								Enrollment temp = enrollmentRecord.readEnrollmentFile();
								readValue = temp.getStudentID();
								if (readValue == studentIDSubMenu) {
									studentRecord.moveFilePointer(studentIDSubMenu - 1);
									Student tempStudentExtract = studentRecord.readStudentFile();
									System.out.println("----Student---");
									tempStudentExtract.printStudent();
									studentRecord.moveFilePointer(studentIDSubMenu - 1);
									System.out.println("----Enroll---");
									temp.printEnrollment();
									System.out.println("----------");
								}
								if (readValue != studentIDSubMenu) {
									enrollmentRecord.moveFilePointer(i + 1);
								}
							}
						}
						if (subOption == 2) { // Grades by Course
							System.out.print("Please enter course cNum to search: ");
							int courseIDSubMenu = keyboard.nextInt();
							while (!verifyCourseCNUM(courseIDSubMenu, courseRecord)) {
								System.out.println("Invalid course cNum");
								System.out.print("Please enter course cNum to search: ");
								courseIDSubMenu = keyboard.nextInt(); // adjust to [0] index
							}
							enrollmentRecord.moveFilePointer(0);
							//System.out.println("RECS: " + enrollmentRecord.getNumberOfRecords()); //TESTING
							int readValue;

							for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
								Enrollment temp = enrollmentRecord.readEnrollmentFile();
								readValue = temp.getcNum();
								if (readValue == courseIDSubMenu) {
									courseRecord.moveFilePointer(courseIDSubMenu - 1);
									Course tempCourseExtract = courseRecord.readCourseFile();
									System.out.println("----Course---");
									tempCourseExtract.printCourse();
									courseRecord.moveFilePointer(courseIDSubMenu - 1);
									System.out.println("----Enroll---");
									temp.printEnrollment();
									System.out.println("----------");
								}
								if (readValue != courseIDSubMenu) {
								//	System.out.println("Here" + i);  //TESTING
									//System.out.println("i+1: " + (i + 1)); //TESTING
									enrollmentRecord.moveFilePointer(i + 1);
								}
							}
						}
						if (subOption == 3) { // Edit Grades by Student
							System.out.print("Please enter student ID to search: ");
							int studentIDSubMenu = keyboard.nextInt();
							while (!verifyStudentID(studentIDSubMenu, studentRecord)) {
								System.out.println("Invalid student ID");
								System.out.print("Please enter student ID to search: ");
								studentIDSubMenu = keyboard.nextInt(); // adjust to [0] index
							}
							enrollmentRecord.moveFilePointer(0);
							int studentIDExtractedFromEnrollment;

							for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
								Enrollment temp = enrollmentRecord.readEnrollmentFile();
								studentIDExtractedFromEnrollment = temp.getStudentID();
								if (studentIDExtractedFromEnrollment == studentIDSubMenu) {
									studentRecord.moveFilePointer(studentIDSubMenu - 1);
									Student tempStudentExtract = studentRecord.readStudentFile();
									System.out.println("----Student---");
									tempStudentExtract.printStudent();
									studentRecord.moveFilePointer(studentIDSubMenu - 1);
									System.out.println("----Enroll---");
									temp.printEnrollment();
									System.out.println("----------");
									keyboard.nextLine();
									System.out.print("Change grade?  Y/N  : ");
									String input = keyboard.nextLine();
									input = input.substring(0, 1);
									String newGrade;
									while(!input.equals("Y") && !input.equals("y") && !input.equals("N") && !input.equals("n")) {
										System.out.println("invalid input must be Y or N");
										System.out.print("Change grade?  Y/N  : ");
										input = keyboard.nextLine();
										input = input.substring(0, 1);
									}
									if(input.equals("Y") || input.equals("y")) {
										System.out.print("[OLD] Grade: " + temp.getGrade().trim() + " ---> [NEW] Grade: ");
										newGrade = keyboard.nextLine();
										temp.setGrade(newGrade);
										enrollmentRecord.moveFilePointer(i);
										enrollmentRecord.writeEnrollmentFile(temp);	
									}
									else {
										System.out.println("No grade changed");
									}
									
								}
								if (studentIDExtractedFromEnrollment != studentIDSubMenu) {
									enrollmentRecord.moveFilePointer(i + 1);
								}
							}
						}
						
						
						if (subOption == 4) { // Edit Grades by Course
							System.out.print("Please enter course number to search: ");
							int courseIDSubMenu = keyboard.nextInt();
							while (!verifyCourseCNUM(courseIDSubMenu, courseRecord)) {
								System.out.println("Invalid course number");
								System.out.print("Please enter course number to search: ");
								courseIDSubMenu = keyboard.nextInt();
							}
							enrollmentRecord.moveFilePointer(0);
							int cNumExtractedFromEnrollment;

							for (int i = 0; i < enrollmentRecord.getNumberOfRecords(); i++) {
								Enrollment temp = enrollmentRecord.readEnrollmentFile();
								cNumExtractedFromEnrollment = temp.getcNum();
								if (cNumExtractedFromEnrollment == courseIDSubMenu) {
									courseRecord.moveFilePointer(courseIDSubMenu - 1);
									Course tempCourseExtract = courseRecord.readCourseFile();
									System.out.println("----Course---");
									tempCourseExtract.printCourse();
									courseRecord.moveFilePointer(courseIDSubMenu - 1);
									System.out.println("----Enroll---");
									temp.printEnrollment();
									System.out.println("----------");
									keyboard.nextLine();
									System.out.print("Change grade?  Y/N  : ");
									String input = keyboard.nextLine();
									input = input.substring(0, 1);
									String newGrade;
									while(!input.equals("Y") && !input.equals("y") && !input.equals("N") && !input.equals("n")) {
										System.out.println("invalid input must be Y or N");
										System.out.print("Change grade?  Y/N  : ");
										input = keyboard.nextLine();
										input = input.substring(0, 1);
									}
									if(input.equals("Y") || input.equals("y")) {
										System.out.print("[OLD] Grade: " + temp.getGrade().trim() + " ---> [NEW] Grade: ");
										newGrade = keyboard.nextLine();
										temp.setGrade(newGrade);
										enrollmentRecord.moveFilePointer(i);
										enrollmentRecord.writeEnrollmentFile(temp);
									}
									else {
										System.out.println("No grade changed");
									}	
								}
								if (cNumExtractedFromEnrollment != courseIDSubMenu) {
									enrollmentRecord.moveFilePointer(i + 1);
								}
							}
						}
					} while (subOption != 0);
				} else {
					System.out.println("No enrollment records found");
					// subOption = 0;
				}
			} else if (menuOption != 0)
				System.out.println("---[Invalid choice...  Try again]---");

		} while (menuOption != 0);
		// close all files
		studentRecord.close();
		courseRecord.close();
		enrollmentRecord.close();
		System.out.println("All files closed.");
	}

}
