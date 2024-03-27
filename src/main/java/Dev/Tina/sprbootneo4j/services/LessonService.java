package Dev.Tina.sprbootneo4j.services;

import Dev.Tina.sprbootneo4j.models.Lesson;
import Dev.Tina.sprbootneo4j.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getAllLessonsByCourseIdentifier(String identifier) {
       return lessonRepository.findLessonsByCourseIdentifier(identifier);
    }
}
