import * as React from 'react';

import EntryTab from '../organisms/EntryTab';

interface EntryPageProps {}

const EntryPage: React.FC<EntryPageProps> = props => (
  <div className="inner-member">
    <div className="ly-member">
      <EntryTab />
    </div>
  </div>
);

export default EntryPage;
