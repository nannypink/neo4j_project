package Dev.Tina.sprbootneo4j.controllers;

import Dev.Tina.sprbootneo4j.models.Course;
import Dev.Tina.sprbootneo4j.objects.CourseDTO;
import Dev.Tina.sprbootneo4j.services.CourseEnrolmentService;
import Dev.Tina.sprbootneo4j.services.CourseService;
import Dev.Tina.sprbootneo4j.services.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;
    private final CourseEnrolmentService courseEnrolmentService;

    public CourseController(CourseService courseService, LessonService lessonService, CourseEnrolmentService courseEnrolmentService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.courseEnrolmentService = courseEnrolmentService;
    }



    @GetMapping("/")
    public ResponseEntity<List<CourseDTO>> courseIndex(Principal principal) {
        List<Course> courses = courseService.getAllCourses();


        List<CourseDTO> responseCourses = courses.stream().map(
                (course) ->  {
                    CourseDTO responseCourse = new CourseDTO(course.getIdentifier(), course.getTitle(), course.getTeacher());
                    responseCourse.setLessons(lessonService.getAllLessonsByCourseIdentifier(course.getIdentifier()));
                    if (principal != null) {
                        responseCourse.setEnrolled(courseEnrolmentService.getEnrolmentStatus(principal.getName(), course.getIdentifier()));
                    }
                    return responseCourse;
                }
        ).collect(Collectors.toList());


        return new ResponseEntity<>(responseCourses, HttpStatus.OK);
    }


    @GetMapping("/{identifier}")
    public ResponseEntity<CourseDTO> courseDetails(@PathVariable String identifier) {
        Course course = courseService.getCourseByIdentifier(identifier);

        CourseDTO responseCourse =  new CourseDTO(course.getIdentifier(), course.getTitle(), course.getTeacher());

        responseCourse.setLessons(lessonService.getAllLessonsByCourseIdentifier(course.getIdentifier()));

        return new ResponseEntity<>(responseCourse, HttpStatus.OK);
    }
}