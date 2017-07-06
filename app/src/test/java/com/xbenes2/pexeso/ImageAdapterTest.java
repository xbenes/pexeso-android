package com.xbenes2.pexeso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ BaseAdapter.class, ImageView.class, View.class, LayoutInflater.class})
public class ImageAdapterTest {
    private static final int IMAGE1 = 1;
    private static final int IMAGE2 = 2;

    private static final int[] images = new int[] {IMAGE1, IMAGE2 };

    private ImageAdapter adapter;

    @Mock
    private ImageView mockImageView;

    @Mock
    private View mockView;

    @Mock
    private View recycled;

    @Mock
    Context mockContext;

    @Mock
    LayoutInflater mockInflater;

    @Before
    public void setup() throws Exception {
        mockImageView = mock(ImageView.class);
        doNothing().when(mockImageView).setTag(anyInt(), anyObject());

        recycled = mock(View.class);
        when(recycled.getTag()).thenReturn(IMAGE1);
        when(recycled.getTag(anyInt())).thenReturn(mockImageView);

        mockView = mock(View.class);
        when(mockView.getTag()).thenReturn(IMAGE1);
        when(mockView.getTag(anyInt())).thenReturn(mockImageView);

        mockStatic(LayoutInflater.class);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), any(ViewGroup.class), eq(false))).thenReturn(mockView);

        adapter = spy(new ImageAdapter(mockContext));
        doNothing().when(adapter).notifyDataSetChanged();
        adapter.setImages(images);
    }

    @Test
    public void test_adapter_returns_correct_size() {
        assertEquals(adapter.getCount(), images.length);
    }

    @Test
    public void test_adapter_returns_correct_image_for_given_position() {
        assertEquals(adapter.getItem(0), IMAGE1);
    }

    @Test
    public void test_adapter_returns_a_view_with_correct_image_set() {
        View result = adapter.getView(0, null, null);

        assertEquals((int) result.getTag(), IMAGE1);
    }

    @Test
    public void test_adapter_recycles_given_view_and_just_updates_image() {
        View result = adapter.getView(0, recycled, null);
        assertEquals(result, recycled);
        assertEquals((int) result.getTag(), IMAGE1);
    }
}
