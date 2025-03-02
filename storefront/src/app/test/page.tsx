'use client';

import React from 'react';

import { storefrontAuth } from '@/apis/user';
import { IStoreFrontAuthResponse } from '@/interfaces/api-response';

export default function Test() {
  const [profile, setProfile] = React.useState<IStoreFrontAuthResponse | null>(
    null
  );
  const [error, setError] = React.useState<string | null>(null);

  React.useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await storefrontAuth();
        setProfile(response);
      } catch (err) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError('An unknown error occurred');
        }
      }
    };
    fetchProfile();
  }, []);

  if (error) {
    return <div>Lỗi: {error}</div>;
  }

  if (!profile) {
    return <div>Đang tải...</div>;
  }

  return (
    <div>
      <h1>Thông Tin Profile</h1>
      {/* Hiển thị dữ liệu profile, ví dụ: */}
      <p>IsAuthenticated: {profile.isAuthenticated}</p>
      <p>Username: {profile.authenticatedUser.userName}</p>
    </div>
  );
}
