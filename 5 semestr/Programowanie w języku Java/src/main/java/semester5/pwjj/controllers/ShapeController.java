package semester5.pwjj.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.services.ShapeService;

import java.util.List;

@Controller
@RequestMapping("/api/shapes")
@RequiredArgsConstructor
public class ShapeController {

	ShapeService shapeService;

	@GetMapping
	public List<Shape> getAllShapes() {
		return shapeService.getAllShapes();
	}

	@PostMapping
	public Shape createShape(@RequestBody final Shape shape) {
		return shapeService.saveShape(shape);
	}
}
