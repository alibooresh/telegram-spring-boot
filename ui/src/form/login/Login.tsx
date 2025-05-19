import React, {useEffect, useState} from 'react';
import axios from "axios";

const LoginForm: React.FC = () => {
    const [step, setStep] = useState<'phone' | 'code' | 'success'>('phone');
    const [phone, setPhone] = useState<string>('');
    const [code, setCode] = useState<string>('');
    const [timer, setTimer] = useState<number>(120);
    const [intervalId, setIntervalId] = useState<NodeJS.Timeout | null>(null);

    const handleSendCode = () => {
        if (!phone.match(/^\d{10,15}$/)) {
            alert('شماره تلفن معتبر وارد کنید');
            return;
        }
        axios.post(`http://localhost:8080/auth/send-phone?phoneNumber=${phone}`)
            .then(value => {
                setStep('code');
                setTimer(120);
            }).catch(reason => {
            alert(reason)
        });
    };

    const handleVerifyCode = () => {
        axios.post(`http://localhost:8080/auth/check-code?code=${code}`)
            .then(value => {
                setStep('success');
                if (intervalId) clearInterval(intervalId);
            }).catch(reason => {
            alert(reason)
        });
    };
    const handleRedirect = () => {
        window.location.replace('http://localhost:8080/swagger-ui/index.html')
    };

    useEffect(() => {
        if (step === 'code') {
            const id = setInterval(() => {
                setTimer((prev) => {
                    if (prev <= 1) {
                        clearInterval(id);
                        return 0;
                    }
                    return prev - 1;
                });
            }, 1000);
            setIntervalId(id);
            return () => clearInterval(id);
        }
    }, [step]);

    const formatTime = (seconds: number): string => {
        const min = Math.floor(seconds / 60);
        const sec = seconds % 60;
        return `${min}:${sec < 10 ? '0' : ''}${sec}`;
    };

    return (
        <div style={styles.container}>
            {step === 'phone' && (
                <>
                    <h2>ورود</h2>
                    <input
                        type="text"
                        placeholder="dsdf"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        style={styles.input}
                    />
                    <button onClick={handleSendCode} style={styles.button}>ارسال کد</button>
                </>
            )}

            {step === 'code' && (
                <>
                    <h2>کد تایید ارسال شد</h2>
                    <p>زمان باقی‌مانده: {formatTime(timer)}</p>
                    <input
                        type="text"
                        placeholder="کد تایید"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                        style={styles.input}
                    />
                    <button onClick={handleVerifyCode} style={styles.button}>تایید</button>
                </>
            )}

            {step === 'success' && (
                <>
                    <h2>وارد شدید!</h2>
                    <button onClick={handleRedirect} style={styles.button}>مشاهده Api ها</button>
                </>

            )}
        </div>
    );
};

const styles: { [key: string]: React.CSSProperties } = {
    container: {
        maxWidth: '300px',
        margin: '50px auto',
        padding: '20px',
        textAlign: 'center',
        background: '#f2f2f2',
        borderRadius: '10px',
        fontFamily: 'sans-serif',
    },
    input: {
        width: '100%',
        padding: '10px',
        margin: '10px 0',
        fontSize: '16px',
    },
    button: {
        width: '100%',
        padding: '10px',
        fontSize: '16px',
        background: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    }
};

export default LoginForm;
