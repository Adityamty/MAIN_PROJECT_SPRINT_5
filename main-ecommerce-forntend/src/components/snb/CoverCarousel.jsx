import React, { useState, useEffect, useRef } from "react";
import img1 from "../../assets/snb/images/cover_image_edited.jpg";
import img2 from "../../assets/snb/images/cover_image_edited_3.jpg";
const images = [
  img1,
  img2
];

const CoverCarousel = () => {
  const [current, setCurrent] = useState(0);
  const carouselRef = useRef(null);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrent((prev) => (prev + 1) % images.length);
    }, 2000);
    return () => clearInterval(timer);
  }, []);

  return (
    <div ref={carouselRef} className="relative w-full overflow-hidden">
      <div
        className="flex transition-transform duration-700 ease-in-out"
        style={{ transform: `translateX(-${current * 100}%)` }}
      >
        {images.map((src, i) => (
          <img
            key={i}
            src={src}
            alt={`slide-${i}`}
            className="w-full h-auto object-cover flex-shrink-0"
          />
        ))}
      </div>
    </div>
  );
};

export default CoverCarousel;
