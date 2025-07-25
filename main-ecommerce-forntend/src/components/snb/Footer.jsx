import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-[#0f0f2d] text-white py-10 font-poppins">
      <div className="max-w-7xl mx-auto px-4">

        {/* Info Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 mb-10">
          {[
            { title: 'Free Shipping', desc: 'Free worldwide shipping.' },
            { title: 'Free Returns', desc: 'Free returns within 15 days' },
            { title: 'Support Online', desc: 'We support customers 24/7' },
          ].map((item, idx) => (
            <div
              key={idx}
              className="bg-[#2e2e4d] text-center p-6 rounded-xl h-full"
            >
              <h4 className="text-lg font-semibold mb-2 !text-white">{item.title}</h4>
              <p className="text-sm !text-white !text-opacity-100 font-light">{item.desc}</p>
            </div>
          ))}
        </div>

        {/* Main Footer */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-10">
          {/* About Us */}
          <div>
            <h5 className="text-xl font-bold mb-3 !text-white">About us</h5>
            <p className="text-sm mb-3 !text-white !text-opacity-100 font-light">
              We only carry designs we believe in ethically and aesthetically – original, authentic pieces that are made to last.
            </p>
            <a href="#" className="text-sm !text-white font-light underline hover:text-blue-400 block mb-4">
              Learn more
            </a>
            <p className="text-sm !text-white font-light">📍 Street Address 2571 Oakridge</p>
            <p className="text-sm !text-white font-light">📞 +1 (973) 435-3638</p>
            <p className="text-sm !text-white font-light">📧 info@fashionwomen.com</p>
          </div>

          {/* Our Company */}
          <div>
            <h5 className="text-xl font-bold mb-3 !text-white">Our Company</h5>
            <ul className="space-y-2 text-sm">
              {['About Us', 'Contact Us', 'Our Store', 'Store Location', 'FAQ'].map((item, i) => (
                <li key={i}>
                  <a href="#" className="!text-white font-light hover:text-blue-400">{item}</a>
                </li>
              ))}
            </ul>
          </div>

          {/* Quick Links */}
          <div>
            <h5 className="text-xl font-bold mb-3 !text-white">Quick links</h5>
            <ul className="space-y-2 text-sm">
              {['Privacy Policy', 'Terms & Conditions', 'Sale', 'Size guide', 'Wishlist', 'Compare'].map((item, i) => (
                <li key={i}>
                  <a href="#" className="!text-white font-light hover:text-blue-400">{item}</a>
                </li>
              ))}
            </ul>
          </div>

          {/* Newsletter */}
          <div>
            <h5 className="text-xl font-bold mb-3 !text-white">Sign Up to Newsletter</h5>
            <p className="text-sm mb-3 !text-white font-light">Subscribe for store updates and discounts.</p>
            <form className="flex mb-3">
              <input
                type="email"
                placeholder="Enter your email..."
                className="flex-grow px-4 rounded-l-md text-black"
              />
              <button
                type="submit"
                className="bg-[#39395c] px-5 rounded-r-md hover:bg-[#4d4d70] transition !text-white"
              >
                SignUp
              </button>
            </form>
            <p className="text-xs !text-white font-light">
              ***By entering the e-mail you accept the{' '}
              <a href="#" className="underline hover:text-blue-400">terms and conditions</a> and the{' '}
              <a href="#" className="underline hover:text-blue-400">privacy policy</a>.
            </p>
            
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
