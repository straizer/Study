package semester5.pwjj.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.repositories.ShapeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShapeService {

	ShapeRepository shapeRepository;

	public Shape saveShape(final Shape shape) {
		return shapeRepository.save(shape);
	}

	public List<Shape> getAllShapes() {
		return shapeRepository.findAll();
	}
}
