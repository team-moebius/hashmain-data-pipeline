import * as React from 'react';

import EntryTab from 'components/organisms/EntryTab';

interface SignPageProps {}

const SignPage: React.FC<SignPageProps> = props => (
  <div className="inner-member">
    <div className="ly-member">
      <EntryTab />
    </div>
  </div>
);

export default SignPage;
