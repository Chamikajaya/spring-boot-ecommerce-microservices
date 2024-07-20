-- Create enum type for payment_method
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_method') THEN
            CREATE TYPE payment_method AS ENUM (
                'VISA',
                'MASTERCARD',
                'AMERICAN_EXPRESS',
                'PAYPAL',
                'GOOGLE_PAY',
                'APPLE_PAY',
                'BITCOIN'
                );
        END IF;
    END$$;