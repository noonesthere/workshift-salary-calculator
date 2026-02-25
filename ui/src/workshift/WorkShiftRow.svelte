<script lang="ts">
  import type { WorkShift } from "../api/types";

  let { shift, onToggle } = $props<{
    shift: WorkShift;
    onToggle: (shift: WorkShift) => void;
  }>();

  function formatDate(date: string) {
    return new Date(date).toLocaleDateString("uk-UA");
  }
</script>

<div
  class="bg-white rounded-xl border border-gray-200
         px-4 py-3
         hover:shadow-sm
         transition-all duration-150
         {shift.included ? '' : 'opacity-50'}"
>
  <div class="flex justify-between items-center">
    <!-- Left -->
    <div class="flex items-center gap-3">
      <input
        type="checkbox"
        checked={shift.included}
        onchange={() => onToggle(shift)}
        class="h-4 w-4 rounded border-gray-300 text-primary-600 focus:ring-primary-500 hover:cursor-pointer"
      />

      <div class="space-y-0.5">
        <div class="text-sm font-semibold text-gray-800">
          {formatDate(shift.startDate)}
        </div>
        <div class="text-xs text-gray-500">
          {shift.beginAt} — {shift.finishedAt}
        </div>
      </div>
    </div>

    <!-- Middle -->
    <div class="text-xs text-gray-600 text-center">
      {shift.workedHours.toFixed(2)} год × {shift.bid} грн
    </div>

    <!-- Right -->
    <div class="text-sm font-semibold text-primary-600">
      {shift.workShiftSalary.toFixed(2)} грн
    </div>
  </div>
</div>
