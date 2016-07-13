// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package in.eweblabs.careeradvance.Interface;

import android.content.Context;

public interface IAsyncTaskRunner
{

    public abstract Context getContext();

    public abstract void taskCompleted(Object obj);

    public abstract void taskErrorMessage(Object obj);

    public abstract void taskProgress(Object obj);

    public abstract void taskStarting();

    public abstract void onCanceled();
}
