package me.demo.util.geo.test;

import com.github.ipcjs.coordtransform.CoordinateTransformUtil;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * 坐标转换-测试用例
 */
public class CoordinateTransformUtilTest {

    @Test
    public void test_bd09ToWgs84() {
        // 百度坐标转WGS84
        double[] wgs84 = CoordinateTransformUtil.bd09ToWgs84(120.698967, 31.324966);
        assertArrayEquals(new double[]{120.68817433685123, 31.320969743354603}, wgs84, 0);
    }

    @Test
    public void test_wgs84ToBd09() {
        // WGS84转百度坐标
        double[] bd09 = CoordinateTransformUtil.wgs84ToBd09(118.3013077, 32.2719040);
        assertArrayEquals(new double[]{118.31309502519456, 32.27614034221775}, bd09, 0);
    }

    @Test
    public void testMalaysiaIsNotInChina() {
        double[] wgs84 = {110.32592, 1.46692};
        double[] gcj02 = CoordinateTransformUtil.wgs84ToGcj02(wgs84[0], wgs84[1]);
        assertArrayEquals(gcj02, wgs84, 0);
    }

    @Test
    public void testHongKongIsNotInChina() {
        double[] HONG_KONG = {114.158321, 22.28451};
        double[] gcj02 = CoordinateTransformUtil.wgs84ToGcj02(HONG_KONG[0], HONG_KONG[1]);
        assertArrayEquals(HONG_KONG, gcj02, 0);
    }
}
