package app.components;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.entities.Instructor;
import app.entities.Room;
import app.entities.Section;
import app.entities.Subject;
import app.repositories.InstructorRepository;
import app.repositories.RoomRepository;
import app.repositories.SectionRepository;
import app.repositories.SubjectRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ScraperComponent {
	
	private static final String URL = "https://aisis.ateneo.edu/j_aisis/classSkeds.do";
    private static final List<String> DEPT_CODES = Arrays.asList(
            "IE", "BIO", "CH", "CHN", "COM", "CEPP", "CPA", "ELM", "DS", "EC", "ECE",
            "EN", "ES", "EU", "FIL", "FAA", "FA", "HSP", "HI", "SOHUM", "DISCS", "SALT",
            "INTAC", "IS", "JSP", "KSP", "LAS", "MAL", "MA", "ML", "NSTP (ADAST)",
            "NSTP (OSCI)", "PH", "PE", "PS", "POS", "PSY", "QMIT", "SB", "SOCSCI",
            "SA", "TH", "TMP"
    );
    
    private static final Map<String, String> DEPT_CODE_TO_DEPT = new HashMap<>();

    static {
    	DEPT_CODE_TO_DEPT.put("IE", "ALL INTERDISCIPLINARY ELECTIVES");
        DEPT_CODE_TO_DEPT.put("BIO", "BIOLOGY");
        DEPT_CODE_TO_DEPT.put("CH", "CHEMISTRY");
        DEPT_CODE_TO_DEPT.put("CHN", "CHINESE STUDIES PROGRAM");
        DEPT_CODE_TO_DEPT.put("COM", "COMMUNICATION");
        DEPT_CODE_TO_DEPT.put("CEPP", "DEPARTMENT OF CATHOLIC EDUCATION PHILOSOPHY AND PRACTICE");
        DEPT_CODE_TO_DEPT.put("CPA", "DEPARTMENT OF CURRICULUM, PEDAGOGY AND ASSESSMENT");
        DEPT_CODE_TO_DEPT.put("ELM", "DEPARTMENT OF EDUCATIONAL LEADERSHIP AND MANAGEMENT");
        DEPT_CODE_TO_DEPT.put("DS", "DEVELOPMENT STUDIES PROGRAM");
        DEPT_CODE_TO_DEPT.put("EC", "ECONOMICS");
        DEPT_CODE_TO_DEPT.put("ECE", "ELECTRONICS, COMPUTER and COMMUNICATIONS ENG.");
        DEPT_CODE_TO_DEPT.put("EN", "ENGLISH");
        DEPT_CODE_TO_DEPT.put("ES", "ENVIRONMENTAL SCIENCE");
        DEPT_CODE_TO_DEPT.put("EU", "EUROPEAN STUDIES");
        DEPT_CODE_TO_DEPT.put("FIL", "FILIPINO");
        DEPT_CODE_TO_DEPT.put("FAA", "FINANCE AND ACCOUNTING");
        DEPT_CODE_TO_DEPT.put("FA", "FINE ARTS");
        DEPT_CODE_TO_DEPT.put("HSP", "HEALTH SCIENCES PROGRAM");
        DEPT_CODE_TO_DEPT.put("HI", "HISTORY");
        DEPT_CODE_TO_DEPT.put("SOHUM", "HUMANITIES");
        DEPT_CODE_TO_DEPT.put("DISCS", "INFORMATION SYSTEMS AND COMPUTER SCIENCE");
        DEPT_CODE_TO_DEPT.put("SALT", "INSTITUTE FOR THE SCIENCE AND ART OF LEARNING AND TEACHING (SALT)");
        DEPT_CODE_TO_DEPT.put("INTAC", "INTAC");
        DEPT_CODE_TO_DEPT.put("IS", "INTERDISCIPLINARY STUDIES DEPARTMENT");
        DEPT_CODE_TO_DEPT.put("JSP", "JAPANESE STUDIES PROGRAM");
        DEPT_CODE_TO_DEPT.put("KSP", "KOREAN STUDIES PROGRAM");
        DEPT_CODE_TO_DEPT.put("LAS", "LEADERSHIP AND STRATEGY");
        DEPT_CODE_TO_DEPT.put("MAL", "MARKETING AND LAW");
        DEPT_CODE_TO_DEPT.put("MA", "MATHEMATICS");
        DEPT_CODE_TO_DEPT.put("ML", "MODERN LANGUAGES");
        DEPT_CODE_TO_DEPT.put("NSTP (ADAST)", "NATIONAL SERVICE TRAINING PROGRAM (ADAST)");
        DEPT_CODE_TO_DEPT.put("NSTP (OSCI)", "NATIONAL SERVICE TRAINING PROGRAM (OSCI)");
        DEPT_CODE_TO_DEPT.put("PH", "PHILOSOPHY");
        DEPT_CODE_TO_DEPT.put("PE", "PHYSICAL EDUCATION");
        DEPT_CODE_TO_DEPT.put("PS", "PHYSICS");
        DEPT_CODE_TO_DEPT.put("POS", "POLITICAL SCIENCE");
        DEPT_CODE_TO_DEPT.put("PSY", "PSYCHOLOGY");
        DEPT_CODE_TO_DEPT.put("QMIT", "QUANTITATIVE METHODS AND INFORMATION TECHNOLOGY");
        DEPT_CODE_TO_DEPT.put("SB", "SCIENCE BLOCK");
        DEPT_CODE_TO_DEPT.put("SOCSCI", "SOCIAL SCIENCES");
        DEPT_CODE_TO_DEPT.put("SA", "SOCIOLOGY/ANTHROPOLOGY");
        DEPT_CODE_TO_DEPT.put("TH", "THEOLOGY");
        DEPT_CODE_TO_DEPT.put("TMP", "THEOLOGY AND MINISTRY PROGRAM");
    }

    @Autowired
    InstructorRepository instructorRepository;
    
    @Autowired
    RoomRepository roomRepository;
    
    @Autowired
    SubjectRepository subjectRepository;
    
    @Autowired
    SectionRepository sectionRepository;

    @Transactional
    public void scrapeAllDepartments() {
        BasicCookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient client = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            System.out.println("Fetching session cookie...");
            HttpGet getRequest = new HttpGet(URL);
            getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0 Safari/537.36");

            try (CloseableHttpResponse getResponse = client.execute(getRequest)) {
                if (getResponse.getCode() == 200) {
                    System.out.println("GET request successful. Session cookie obtained.");
                } else {
                    System.err.println("Failed to fetch session cookie. Status: " + getResponse.getCode());
                    return;
                }
            }

            for (String deptCode : DEPT_CODES) {
                System.out.println("Fetching schedules for department: " + deptCode);
                HttpPost postRequest = new HttpPost(URL);

                postRequest.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                postRequest.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
                postRequest.setHeader("Cache-Control", "max-age=0");
                postRequest.setHeader("Connection", "keep-alive");
                postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
                postRequest.setHeader("Origin", "https://aisis.ateneo.edu");
                postRequest.setHeader("Referer", "https://aisis.ateneo.edu/j_aisis/classSkeds.do");
                postRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0 Safari/537.36");

                String payload = "command=displayResults&subjCode=ALL&applicablePeriod=2024-2&deptCode=" + deptCode;
                postRequest.setEntity(new StringEntity(payload, ContentType.APPLICATION_FORM_URLENCODED));

                try (CloseableHttpResponse postResponse = client.execute(postRequest)) {
                    if (postResponse.getCode() == 200) {
                        String responseBody = EntityUtils.toString(postResponse.getEntity());
                        parseAndStoreSchedules(responseBody, deptCode);
                    } else {
                        System.err.println("Failed to fetch schedules for department " + deptCode + ". Status: " + postResponse.getCode());
                    }
                }

                TimeUnit.SECONDS.sleep(5);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAndStoreSchedules(String html, String deptCode) {
        Document document = Jsoup.parse(html);
        Element table = document.getElementById("example");

        if (table != null) {
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Elements cols = rows.get(i).select("td");
                if (cols.size() >= 7) {
                    String subjectCode = cols.get(0).text().trim();
                    String section = cols.get(1).text().trim();
                    String courseTitle = cols.get(2).text().trim();
                    String time = cols.get(4).text().trim();
                    String roomName = cols.get(5).text().trim();
                    String instructorName = cols.get(6).text().trim();

                    saveSchedule(subjectCode, section, courseTitle, time, roomName, instructorName, deptCode);
                }
            }
        } else {
            System.out.println("No schedules found for Department: " + deptCode);
        }
    }
    
    public void saveSchedule(String subjectCode, String section, String courseTitle, String time, String roomName, String instructorName, String deptCode) {
        try {
            String[] timeParts = time.split(" ");
            // Time Schedule must conform to "M", "T", "W", "TH", "F", "SAT", "M-TH", or "T-F". Otherwise, not included.
            Set<String> validDays = new HashSet<>(Arrays.asList("M", "T", "W", "TH", "F", "SAT", "M-TH", "T-F"));
            
            if (!validDays.contains(timeParts[0])) {
                System.out.println("Skipping invalid day of the week: " + time);
                return;
            }

            String dayOfTheWeekSchedule = timeParts[0];
            String[] timeRange = timeParts[1].split("-");
            if (timeRange.length != 2) {
                System.out.println("Skipping invalid time range: " + time);
                return;
            }
            String timeStart = timeRange[0];
            String timeEnd = timeRange[1];
            
        	Instructor instructor = instructorRepository.findByInstructorName(instructorName);
            if (instructor == null) {
                instructor = new Instructor();
                instructor.setInstructorName(instructorName);
                instructor = instructorRepository.save(instructor);
            }

            Room room = roomRepository.findByRoomName(roomName);
            if (room == null) {
                room = new Room();
                room.setRoomName(roomName);
                room = roomRepository.save(room);
            }

            Subject subject = subjectRepository.findBySubjectCode(subjectCode);
            if (subject == null) {
                subject = new Subject();
                subject.setSubjectCode(subjectCode);
                subject.setCourseTitle(courseTitle);
                subject.setDepartment(deptCode);
                subject = subjectRepository.save(subject);
            }

            Section sectionEntity = sectionRepository.findBySectionName(section);
            if (sectionEntity == null) {
                sectionEntity = new Section();
                sectionEntity.setSectionName(section);
                sectionEntity.setDayOfTheWeekSchedule(dayOfTheWeekSchedule);
                sectionEntity.setTimeStart(timeStart);
                sectionEntity.setTimeEnd(timeEnd);
                sectionEntity.setRoom(room);
                sectionEntity.setSubject(subject);
                sectionEntity.getInstructors().add(instructor);
                sectionRepository.save(sectionEntity);
            }
        } catch (Exception e) {
            System.err.println("Error saving schedule: " + e.getMessage());
        }
    }
}
