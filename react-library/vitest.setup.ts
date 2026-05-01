import '@testing-library/jest-dom';
import { cleanup } from '@testing-library/react';
import { afterEach } from 'vitest';

// Runs cleanup after each test file to unmount React components and clean up the DOM
afterEach(() => {
  cleanup();
});
