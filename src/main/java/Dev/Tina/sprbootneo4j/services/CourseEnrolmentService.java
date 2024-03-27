package Dev.Tina.sprbootneo4j.services;

import Dev.Tina.sprbootneo4j.models.Course;
import Dev.Tina.sprbootneo4j.queryResults.CourseEnrolmentQueryResult;
import Dev.Tina.sprbootneo4j.repositories.CourseRepository;
import Dev.Tina.sprbootneo4j.repositories.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseEnrolmentService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseEnrolmentService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Boolean getEnrolmentStatus(String username, String identifier) {
        return userRepository.findEnrolmentStatus(username, identifier);
    }

    public CourseEnrolmentQueryResult enrolIn(String username, String identifier) {
        if (getEnrolmentStatus(username, identifier)) {
            String errorMessage = "User " + username + " is already enrolled in the course ID: " + identifier;
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
        return userRepository.createEnrolmentRelationship(username, identifier);
    }

    public List<Course> getAllEnrolledCoursesByUsername(String username) {
        return courseRepository.findAllEnrolledCoursesByUsername(username);
    }
}
