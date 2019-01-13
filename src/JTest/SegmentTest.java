package JTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Algorithm.segment;
import GIS.Rec;
import Geom.Point3D;


class SegmentTest {

	// create the boxes in example 5
	String line ="B,1,32.1027021891685,35.208183800241585,0.0,32.1049811766491,35.20851770033915,0.0,1.0";
	String line1 ="B,2,32.10354703120936,35.2056556995029,0.0,32.10395902208114,35.210616500952405,0.0,1.0";
	String[] array = line.split(",");
	String[] array1 = line1.split(",");

	Rec r = new Rec (array);
	Rec r1 = new Rec (array1);


	@Test
	void testAdd() {

		ArrayList<Rec> boxs = new ArrayList<Rec>();
		boxs.add(r);
		boxs.add(r1);

		segment s = new segment(boxs);
		Point3D p  = new Point3D(32.10387,35.20660);
		Point3D p1  = new Point3D(32.10310,35.20828);
		assertFalse(!s.Intersect(p, p1));

		Point3D p2  = new Point3D(32.10305,35.20351);
		Point3D p3  = new Point3D(32.10457,35.20352);

		assertFalse(s.Intersect(p3, p2));


	}
}
