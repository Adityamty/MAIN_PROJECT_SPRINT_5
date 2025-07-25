import React, { useState } from 'react';
import { X, Ruler } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const SizeChart = ({ isOpen, onClose, category = 'General' }) => {
  const [activeTab, setActiveTab] = useState('cm');

  const sizeCharts = {
    'General': {
      cm: [
        { size: 'XS', chest: '84-89', waist: '71-76', hips: '89-94' },
        { size: 'S', chest: '89-94', waist: '76-81', hips: '94-99' },
        { size: 'M', chest: '94-99', waist: '81-86', hips: '99-104' },
        { size: 'L', chest: '99-104', waist: '86-91', hips: '104-109' },
        { size: 'XL', chest: '104-109', waist: '91-96', hips: '109-114' },
        { size: 'XXL', chest: '109-114', waist: '96-101', hips: '114-119' },
      ],
      inches: [
        { size: 'XS', chest: '33-35', waist: '28-30', hips: '35-37' },
        { size: 'S', chest: '35-37', waist: '30-32', hips: '37-39' },
        { size: 'M', chest: '37-39', waist: '32-34', hips: '39-41' },
        { size: 'L', chest: '39-41', waist: '34-36', hips: '41-43' },
        { size: 'XL', chest: '41-43', waist: '36-38', hips: '43-45' },
        { size: 'XXL', chest: '43-45', waist: '38-40', hips: '45-47' },
      ]
    },
    'Men': {
      cm: [
        { size: 'XS', chest: '86-91', waist: '71-76', hips: '86-91' },
        { size: 'S', chest: '91-96', waist: '76-81', hips: '91-96' },
        { size: 'M', chest: '96-101', waist: '81-86', hips: '96-101' },
        { size: 'L', chest: '101-106', waist: '86-91', hips: '101-106' },
        { size: 'XL', chest: '106-111', waist: '91-96', hips: '106-111' },
        { size: 'XXL', chest: '111-116', waist: '96-101', hips: '111-116' },
      ],
      inches: [
        { size: 'XS', chest: '34-36', waist: '28-30', hips: '34-36' },
        { size: 'S', chest: '36-38', waist: '30-32', hips: '36-38' },
        { size: 'M', chest: '38-40', waist: '32-34', hips: '38-40' },
        { size: 'L', chest: '40-42', waist: '34-36', hips: '40-42' },
        { size: 'XL', chest: '42-44', waist: '36-38', hips: '42-44' },
        { size: 'XXL', chest: '44-46', waist: '38-40', hips: '44-46' },
      ]
    },
    'Women': {
      cm: [
        { size: 'XS', chest: '81-86', waist: '66-71', hips: '89-94' },
        { size: 'S', chest: '86-91', waist: '71-76', hips: '94-99' },
        { size: 'M', chest: '91-96', waist: '76-81', hips: '99-104' },
        { size: 'L', chest: '96-101', waist: '81-86', hips: '104-109' },
        { size: 'XL', chest: '101-106', waist: '86-91', hips: '109-114' },
        { size: 'XXL', chest: '106-111', waist: '91-96', hips: '114-119' },
      ],
      inches: [
        { size: 'XS', chest: '32-34', waist: '26-28', hips: '35-37' },
        { size: 'S', chest: '34-36', waist: '28-30', hips: '37-39' },
        { size: 'M', chest: '36-38', waist: '30-32', hips: '39-41' },
        { size: 'L', chest: '38-40', waist: '32-34', hips: '41-43' },
        { size: 'XL', chest: '40-42', waist: '34-36', hips: '43-45' },
        { size: 'XXL', chest: '42-44', waist: '36-38', hips: '45-47' },
      ]
    }
  };

  const currentChart = sizeCharts[category] || sizeCharts['General'];
  const measurements = currentChart[activeTab];

  if (!isOpen) return null;

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4"
        onClick={onClose}
      >
        <motion.div
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.9, opacity: 0 }}
          className="bg-white dark:bg-gray-800 rounded-lg max-w-2xl w-full max-h-[90vh] overflow-y-auto"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="p-6">
            {/* Header */}
            <div className="flex items-center justify-between mb-6">
              <div className="flex items-center gap-2">
                <Ruler className="w-6 h-6 text-blue-600" />
                <h2 className="text-2xl font-bold text-gray-900 dark:text-white">
                  Size Chart - {category}
                </h2>
              </div>
              <button
                onClick={onClose}
                className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200"
              >
                <X className="w-6 h-6" />
              </button>
            </div>

            {/* Unit Tabs */}
            <div className="flex gap-2 mb-6">
              <button
                onClick={() => setActiveTab('cm')}
                className={`px-4 py-2 rounded-lg font-medium transition-colors ${
                  activeTab === 'cm'
                    ? 'bg-[#a0522d] text-white'
                    : 'bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600'
                }`}
              >
                Centimeters
              </button>
              <button
                onClick={() => setActiveTab('inches')}
                className={`px-4 py-2 rounded-lg font-medium transition-colors ${
                  activeTab === 'inches'
                    ? 'bg-[#a0522d] text-white'
                    : 'bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600'
                }`}
              >
                Inches
              </button>
            </div>

            {/* Size Chart Table */}
            <div className="overflow-x-auto">
              <table className="w-full border-collapse border border-gray-300 dark:border-gray-600">
                <thead>
                  <tr className="bg-gray-50 dark:bg-gray-700">
                    <th className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-left font-semibold text-gray-900 dark:text-white">
                      Size
                    </th>
                    <th className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-left font-semibold text-gray-900 dark:text-white">
                      Chest ({activeTab})
                    </th>
                    <th className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-left font-semibold text-gray-900 dark:text-white">
                      Waist ({activeTab})
                    </th>
                    <th className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-left font-semibold text-gray-900 dark:text-white">
                      Hips ({activeTab})
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {measurements.map((measurement, index) => (
                    <tr key={measurement.size} className={index % 2 === 0 ? 'bg-white dark:bg-gray-800' : 'bg-gray-50 dark:bg-gray-750'}>
                      <td className="border border-gray-300 dark:border-gray-600 px-4 py-2 font-medium text-gray-900 dark:text-white">
                        {measurement.size}
                      </td>
                      <td className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-gray-700 dark:text-gray-300">
                        {measurement.chest}
                      </td>
                      <td className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-gray-700 dark:text-gray-300">
                        {measurement.waist}
                      </td>
                      <td className="border border-gray-300 dark:border-gray-600 px-4 py-2 text-gray-700 dark:text-gray-300">
                        {measurement.hips}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {/* How to Measure */}
            <div className="mt-6 bg-blue-50 dark:bg-blue-900/30 rounded-lg p-4">
              <h3 className="font-semibold text-blue-900 dark:text-blue-100 mb-3">How to Measure:</h3>
              <div className="space-y-2 text-sm text-blue-800 dark:text-blue-200">
                <p><strong>Chest:</strong> Measure around the fullest part of your chest, keeping the tape horizontal.</p>
                <p><strong>Waist:</strong> Measure around your natural waistline, keeping the tape horizontal.</p>
                <p><strong>Hips:</strong> Measure around the fullest part of your hips, keeping the tape horizontal.</p>
              </div>
            </div>
          </div>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default SizeChart;
