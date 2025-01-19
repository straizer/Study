package semester5.pwjj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semester5.pwjj.entities.shapes.Shape;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
}
