import React from 'react';
import TopHeader from '../../components/snb/headers/TopHeader';
import MainHeader from '../../components/snb/headers/MainHeader';
import Footer from '../../components/snb/Footer';

import CoverCarousel from '../../components/snb/CoverCarousel';
import CategoryGrid from '../../components/snb/CategoryGrid';

const Home = () => {
  return (
    <>
      <TopHeader />
      <MainHeader />

      {/* Hero Section */}
      
      <CoverCarousel/>
      <div className="bg-gray-900 !text-white text-center py-16">
        <h4 className="text-lg font-medium tracking-wider mb-2 !text-white">BIG SALE 20% OFF</h4>
        <h1 className="text-4xl md:text-5xl font-bold !text-white">Flash Sale Alert!</h1>
      </div>

      <CategoryGrid />
      {/* <BestDeals /> */}
      <Footer />
    </>
  );
};

export default Home;
