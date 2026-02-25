<script lang="ts">
  import { onMount } from "svelte";
  import api from "../api/api";
  import type { WorkShift } from "../api/types";
  import WorkShiftRow from "./WorkShiftRow.svelte";

  let workShifts = $state<WorkShift[]>([]);
  let loading = $state(true);
  let error = $state<string | null>(null);

  let {from, to} = $props();

  const totalHours = $derived(
    workShifts
      .filter(s => s.included)
      .reduce((sum, s) => sum + s.workedHours, 0)
  );

  const totalSalary = $derived(
    workShifts
      .filter(s => s.included)
      .reduce((sum, s) => sum + s.workShiftSalary, 0)
  );

  const totalCount = $derived(
    workShifts.filter(s => s.included).length
  );

  async function toggleIncluded(shift: WorkShift) {
    const updated = { ...shift, included: !shift.included };

    // Optimistic UI update
    workShifts = workShifts.map(s =>
      s.id === shift.id ? updated : s
    );

    try {
      await api.patch(`workshifts/${shift.id}`, {
        included: updated.included
      });
    } catch (e) {
      console.error(e);

      // rollback if failed
      workShifts = workShifts.map(s =>
        s.id === shift.id ? shift : s
      );
    }
  }

  async function load() {
    loading = true;
    error = null;

    try {
    const params = new URLSearchParams({
      from,
      to
    });

      workShifts = await api.get<WorkShift[]>(
        `workshifts?${params.toString()}`
      );
    } catch (e) {
      console.error(e);
      error = "Не вдалося завантажити зміни";
    } finally {
      loading = false;
    }
  }

  $effect(() => {
    if (!from || !to) return;
    load();
  });

  function formatDate(date: string) {
    return new Date(date).toLocaleDateString("uk-UA");
  }

  export function addShift(saved: WorkShift) {
     if (saved.startDate < from || saved.startDate > to) return;

      workShifts = [
        saved,
        ...workShifts.filter(s => s.id !== saved.id)
      ];
  }

</script>

<div class="max-w-4xl mx-auto p-6 space-y-6">
  <!-- Header -->
  <div class="flex items-center justify-between">
    <h2 class="text-2xl font-bold text-gray-900">
      Робочі зміни
    </h2>

    {#if !loading && !error}
      <div class="flex gap-6 text-sm text-gray-700 bg-white px-4 py-2 rounded-xl shadow-sm border">

        <div>
          <span class="text-gray-500">Змін:</span>
          <span class="font-semibold ml-1">
            {totalCount}
          </span>
        </div>

        <div>
          <span class="text-gray-500">Годин:</span>
          <span class="font-semibold ml-1">
            {totalHours.toFixed(2)}
          </span>
        </div>

        <div>
          <span class="text-gray-500">Сума:</span>
          <span class="font-semibold text-primary-600 ml-1">
            {totalSalary.toFixed(2)} грн
          </span>
        </div>

      </div>
    {/if}
  </div>

  {#if loading}
    <div class="text-center py-10 text-gray-500 animate-pulse">
      Завантаження...
    </div>
  {:else if error}
    <div class="bg-red-50 text-red-600 p-4 rounded-xl">
      {error}
    </div>
  {:else if workShifts.length === 0}
    <div class="bg-gray-50 text-gray-500 p-6 rounded-2xl text-center">
      Немає збережених змін
    </div>
  {:else}
    <div class="grid gap-4">
      {#each workShifts as shift (shift.id)}
        <WorkShiftRow
          {shift}
          onToggle={toggleIncluded}
        />
      {/each}
    </div>
  {/if}
</div>
