import React from 'react';
import Call from '../../../assets/snb/images/call.svg';
import Address from '../../../assets/snb/images/address.svg';

const TopHeader = () => {
  return (
    <div className="bg-gray-900 text-gray-100 text-sm px-5 py-2 font-medium">
      <div className="flex flex-col md:flex-row justify-between items-center gap-2 px-5">
        <span>Welcome to FashionHub!</span>
        <span className="flex items-center flex-wrap justify-center gap-2">
          <img src={Address} alt="address" className="w-5 h-4 inline-block" />
          Street Address 2571 Oakridge
          <span className="mx-1">|</span>
          <img src={Call} alt="call" className="w-5 h-4 inline-block" />
          +91-98765-43210
        </span>
      </div>
    </div>
  );
};

export default TopHeader;
