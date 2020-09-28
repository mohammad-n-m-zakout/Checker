//package com.example.emu;
//
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.IInterface;
//import android.os.Parcel;
//import android.os.RemoteException;
//
//public interface IEmulatorCheck extends IInterface {
//    boolean isEmulator() throws RemoteException;
//
//    void kill() throws RemoteException;
//
//    abstract class Stub extends Binder implements IEmulatorCheck {
//        private static final String DESCRIPTOR = "com.example.emu.IEmulatorCheck";
//        static final int TRANSACTION_isEmulator = 1;
//        static final int TRANSACTION_kill = 2;
//
//        public Stub() {
//            this.attachInterface(this, "com.example.emu.IEmulatorCheck");
//        }
//
//        public static IEmulatorCheck asInterface(IBinder obj) {
//            if (obj == null) {
//                return null;
//            } else {
//                IInterface iin = obj.queryLocalInterface("com.example.emu.IEmulatorCheck");
//                return (IEmulatorCheck) (iin != null && iin instanceof IEmulatorCheck ? (IEmulatorCheck) iin : new IEmulatorCheck.Stub.Proxy(obj));
//            }
//        }
//
//        public IBinder asBinder() {
//            return this;
//        }
//
//        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
//            String descriptor = "com.example.emu.IEmulatorCheck";
//            switch (code) {
//                case 1:
//                    data.enforceInterface(descriptor);
//                    boolean _result = this.isEmulator();
//                    reply.writeNoException();
//                    reply.writeInt(_result ? 1 : 0);
//                    return true;
//                case 2:
//                    data.enforceInterface(descriptor);
//                    this.kill();
//                    reply.writeNoException();
//                    return true;
//                case 1598968902:
//                    reply.writeString(descriptor);
//                    return true;
//                default:
//                    return super.onTransact(code, data, reply, flags);
//            }
//        }
//
//        private static class Proxy implements IEmulatorCheck {
//            private IBinder mRemote;
//
//            Proxy(IBinder remote) {
//                this.mRemote = remote;
//            }
//
//            public IBinder asBinder() {
//                return this.mRemote;
//            }
//
//            public String getInterfaceDescriptor() {
//                return "com.example.emu.IEmulatorCheck";
//            }
//
//            public boolean isEmulator() throws RemoteException {
//                Parcel _data = Parcel.obtain();
//                Parcel _reply = Parcel.obtain();
//
//                boolean _result;
//                try {
//                    _data.writeInterfaceToken("com.example.emu.IEmulatorCheck");
//                    this.mRemote.transact(1, _data, _reply, 0);
//                    _reply.readException();
//                    _result = 0 != _reply.readInt();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//
//                return _result;
//            }
//
//            public void kill() throws RemoteException {
//                Parcel _data = Parcel.obtain();
//                Parcel _reply = Parcel.obtain();
//
//                try {
//                    _data.writeInterfaceToken("com.example.emu.IEmulatorCheck");
//                    this.mRemote.transact(2, _data, _reply, 0);
//                    _reply.readException();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//
//            }
//        }
//    }
//}
