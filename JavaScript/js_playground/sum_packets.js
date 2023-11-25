// Usage: node sum_packets.js

function sentPackets(hops, packets) {
    if (!hops.length || !packets.length) {
        return 0;
    }

    const packetsByHop = {};

    for (let i = 0; i < hops.length; i++) {
        const hop = hops[i];
        const packet = packets[i];
        packetsByHop[hop] = (packetsByHop[hop] || 0) + packet;
    }

    return packetsByHop[hops[0]] || 0;
}

function assert(condition, message) {
    if (!condition) {
        throw new Error(message || 'Assertion failed');
    }
}

function testSentPackets() {
    // Test 1: Empty arrays
    const result1 = sentPackets([], []);
    assert(result1 === 0, 'Test 1 failed');

    // Test 2: Empty 'hops' array
    const result2 = sentPackets([], [1, 2, 3]);
    assert(result2 === 0, 'Test 2 failed');

    // Test 3: Empty 'packets' array
    const result3 = sentPackets(['a', 'b', 'c'], []);
    assert(result3 === 0, 'Test 3 failed');

    // Test 4: Non-empty arrays
    const hops4 = ['a', 'b', 'c', 'a', 'b', 'c'];
    const packets4 = [1, 1, 1, 3, 3, 3];
    const result4 = sentPackets(hops4, packets4);
    assert(result4 === 4, 'Test 4 failed');

    console.log('All tests passed successfully!');
}

testSentPackets();
